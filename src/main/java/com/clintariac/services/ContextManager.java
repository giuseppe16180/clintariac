package com.clintariac.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.clintariac.data.EmailData;
import com.clintariac.data.MessageData;
import com.clintariac.data.TicketData;
import com.clintariac.data.TicketState;
import com.clintariac.data.UserData;
import com.clintariac.services.config.Credentials;
import com.clintariac.services.config.Preferences;
import com.clintariac.services.config.StandardEmails;
import com.clintariac.services.utils.AppUtils;
import com.clintariac.services.utils.EmailHandler;
import com.clintariac.services.utils.Procedure;
import com.clintariac.services.utils.SingletonException;
import com.clintariac.services.utils.TicketNotFoundException;
import com.clintariac.services.utils.UserNotFoundException;

/**
 * ContextManager
 * 
 * Classe predisposta ad implementare la logica dell'applicativo.
 */

public class ContextManager {

    private static boolean isInstantiated = false;

    private DataManager dataManager;
    private EmailManager emailManager;

    private Consumer<EmailData> emailHandler;

    private ScheduledExecutorService executor;
    private Future<?> task;

    private boolean shouldUpdate;
    private Procedure onReload;
    private Procedure onUpdate;

    /**
     * Costruttore di ContextManager.
     * 
     * <p>
     * Permette di gestire la logica principale dell'applicativo.
     * </p>
     * 
     * <p>
     * Si tratta di un singleton, in quanto facendo accesso a varie risorse, tra le quali dei file e
     * una casella di posta elettronica, è opportuno che non ne esistano più istanze. In caso di
     * tentata istanziazione multipla, viene lanciata un'eccezione.
     * </p>
     * 
     * <p>
     * Permette di leggere e scrivere sulle liste dei ticket e degli user, in maniera persistente
     * tra i vari avvii dell'applicazione. Contiene i metodi per alterare le strutture dati in
     * maniera limitata a quelle che dovrebbero essere le interazioni scaturite dall'interfaccia
     * utente.
     * </p>
     * 
     * <p>
     * Sulla base delle interazioni effettuate sulle strutture dati, invia delle email verso gli
     * utenti interessati, per notificarli circa i loro appuntamenti, per comunicare le istruzioni
     * in seguito alla loro registrazione, avvertirli in caso di errore etc.
     * </p>
     * 
     * <p>
     * Permette di configurare un processo eseguito ciclicamente in background per gestire
     * l'aggiornamento dell'applicativo, in seguito alla ricezione di nuove email.
     * </p>
     */
    public ContextManager() {

        if (ContextManager.isInstantiated == true) {
            throw new SingletonException();

        } else {

            executor = Executors.newSingleThreadScheduledExecutor();

            dataManager = new DataManager();
            emailManager = new GmailManager(Credentials.username, Credentials.password);

            initEmailHandler();

            shouldUpdate = false;
            isInstantiated = true;

            onReload = Procedure.doNothing();
            onUpdate = Procedure.doNothing();
        }
    }

    /**
     * <p>
     * Metodo che permette di caricare in memoria le strutture dati, partendo dal contenuto
     * precedentemente scritto in memoria secondaria. Deve essere chiamato prima di procedere alle
     * operazioni sui dati.
     * </p>
     * 
     * <p>
     * In seguito al caricamento viene lanciato anche l'aggiornamento della lista di ticket, con il
     * pull delle nuove email e l'alterazione delle prenotazioni in attesa e scadute.
     * </p>
     */
    public void loadData() {
        dataManager.loadUsersList();
        dataManager.loadTicketsList();
        update();
    }

    /**
     * Metodo per aggiungere un evento in caso di eccezione sull'IO delle strutture dati su file.
     * 
     * @param onException metodo da chiamare in caso di eccezione sull'IO
     */
    public void addOnDataException(Consumer<Exception> onException) {
        dataManager.addOnException(onException);
    }

    /**
     * Metodo per aggiungere un evento in caso di eccezione in fase di invio o di ricezione delle
     * email.
     * 
     * @param onException metodo da chiamare in caso di eccezione con le email
     */
    public void addOnEmailException(Consumer<Exception> onException) {
        emailManager.addOnException(onException);
    }

    /**
     * Metodo per inizializzare {@code emailHandler}.
     * 
     * <p>
     * Predispone una serie di {@code Predicate<EmailData>} e {@code Consumer<EmailData>} per poter
     * realizzare poi una chain of responsibility, capace di gestire in maniera esaustiva le
     * possibili email in arrivo al {@code ContextManager}.
     * </p>
     */
    private void initEmailHandler() {

        String comunToken = "comunicazione";
        String deleteToken = "annulla";
        String confirmToken = "conferma";


        final Predicate<EmailData> isUserNotPresent =
                email -> !dataManager.getUserByEmail(email.address).isPresent();

        final Consumer<EmailData> consumeUserNotPresent = email -> {
            emailManager.send(new EmailData(
                    email.address, "Utente non registrato",
                    StandardEmails.UNREGISTERED));
        };


        final Predicate<EmailData> isNewMessage =
                email -> email.subject.toLowerCase().contains(comunToken);

        final Consumer<EmailData> consumeNewMessage = email -> {

            UserData user = dataManager.getUserByEmail(email.address).get();

            addToChat(user.id, email.message, true);

            Optional<TicketData> oldWaitingTicket = dataManager.getTicketsList().stream()
                    .filter(ticket -> ticket.user.equals(user.id)
                            && ticket.state == TicketState.AWAITING)
                    .findFirst();

            if (oldWaitingTicket.isPresent()) {
                dataManager.deleteTicket(oldWaitingTicket.get().id);
            }

            String newTicketId = dataManager.newTicketId();

            dataManager.setTicket(new TicketData(
                    newTicketId,
                    user.id,
                    TicketState.AWAITING,
                    LocalDateTime.MAX,
                    LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
                    email.message));
        };



        final Predicate<EmailData> isNotValidSubject = email -> {
            String subject = email.subject.toLowerCase();
            return Stream.of(deleteToken, confirmToken).noneMatch(token -> token.equals(subject));
        };

        final Consumer<EmailData> consumeNotValidSubject = email -> {
            emailManager.send(new EmailData(
                    email.address, "È stato riscontrato un errore",
                    StandardEmails.ERROR));
        };



        final Predicate<EmailData> isNotValidTicket = (email) -> {
            Optional<TicketData> ticket = dataManager.getTicket(email.message.split("\\s+")[0]);
            UserData user = dataManager.getUserByEmail(email.address).get();
            return ticket.isEmpty() || !ticket.get().user.equals(user.id);
        };

        final Consumer<EmailData> consumeNotValidTicket = email -> {
            emailManager.send(new EmailData(
                    email.address, "Ticket non valido",
                    StandardEmails.NOT_VALID));
        };



        final Predicate<EmailData> isConfirmTicket =
                email -> email.subject.toLowerCase().equals(confirmToken);

        final Consumer<EmailData> consumeConfirmTicket = email -> {

            TicketData ticket = dataManager.getTicket(email.message.split("\\s+")[0]).get();
            String dateTime[] = AppUtils.localDateTimeToString(ticket.booking).split(" ");

            if (ticket.state != TicketState.AWAITING && ticket.state != TicketState.DELETED) {

                if (ticket.state != TicketState.CONFIRMED) {

                    boolean isSent = emailManager.send(new EmailData(
                            email.address, "Appuntamento confermato",
                            StandardEmails.confirmMessage(dateTime[0], dateTime[1], ticket.id)));

                    if (isSent) {
                        addToChat(ticket.user, "Conferma - " + ticket.id, true);
                        dataManager.setTicket(new TicketData(
                                ticket.id,
                                ticket.user,
                                TicketState.CONFIRMED,
                                ticket.booking,
                                LocalDateTime.now(),
                                ticket.message));
                    }
                } else {
                    emailManager.send(new EmailData(
                            email.address, "Appuntamento già confermato",
                            StandardEmails.confirmMessage(dateTime[0], dateTime[1], ticket.id)));
                }
            } else {
                emailManager.send(new EmailData(
                        email.address, "Si è verificato un errore",
                        StandardEmails.ERROR));
            }
        };



        final Predicate<EmailData> isDeleteTicket =
                email -> email.subject.toLowerCase().equals(deleteToken);

        final Consumer<EmailData> consumeDeleteTicket = email -> {

            TicketData ticket = dataManager.getTicket(email.message.split("\\s+")[0]).get();

            if (ticket.state == TicketState.AWAITING || ticket.state == TicketState.BOOKED) {

                if (ticket.state != TicketState.DELETED) {
                    boolean isSent = emailManager.send(new EmailData(
                            email.address, "Appuntamento cancellato",
                            StandardEmails.deleteMessage(ticket.id)));

                    if (isSent) {
                        addToChat(ticket.user, "Annulla - " + ticket.id, true);
                        dataManager.deleteTicket(ticket.id);
                    }
                } else {
                    emailManager.send(new EmailData(
                            email.address, "Appuntamento già cancellato",
                            StandardEmails.deleteMessage(ticket.id)));
                }
            } else {
                emailManager.send(new EmailData(
                        email.address,
                        "Si è verificato un errore",
                        StandardEmails.ERROR));
            }
        };


        emailHandler = email -> Stream.of(
                EmailHandler.of(isUserNotPresent, consumeUserNotPresent),
                EmailHandler.of(isNewMessage, consumeNewMessage),
                EmailHandler.of(isNotValidSubject, consumeNotValidSubject),
                EmailHandler.of(isNotValidTicket, consumeNotValidTicket),
                EmailHandler.of(isConfirmTicket, consumeConfirmTicket),
                EmailHandler.of(isDeleteTicket, consumeDeleteTicket))
                .reduce(EmailHandler.identity(), (before, after) -> before.andThen(after))
                .apply(Optional.of(email));
    }

    /**
     * Metodo che aggiorna il context facendo:
     * <ul>
     * <li>pull ed elaborazione delle email non lette</li>
     * <li>elaborazione delle richieste di prenotazione scadute</li>
     * </ul>
     * <p>
     * In seguito all'avvenuto aggiornamento esegue la procedura {@code onUpdate} per lanciare gli
     * aggiornamenti richiesti dai client del context.
     * </p>
     */
    public void update() {
        emailManager.pull().stream().forEach(emailHandler::accept);
        expireBooked();
        if (shouldUpdate) {
            onUpdate.run();
        }
    }


    /**
     * Metodo per creare un nuovo ticket (in stato di attesa di conferma) per un utente ad una
     * specifica data-ora. L'utente viene notificato via email.
     * 
     * @param userId
     * @param dateTime
     */
    public void createNewTicket(String userId, LocalDateTime dateTime) {

        String dateAndTime[] = AppUtils.localDateTimeToString(dateTime).split(" ");

        String ticketId = dataManager.newTicketId();

        boolean isSent = sendEmail(
                userId,
                "Proposta appuntamento",
                StandardEmails.ticketMessage(dateAndTime[0], dateAndTime[1], ticketId));

        if (isSent) {

            addToChat(userId, String.format("Proposta %s - %s %s",
                    ticketId, dateAndTime[0], dateAndTime[1]), false);

            dataManager.setTicket(new TicketData(
                    ticketId,
                    userId,
                    TicketState.BOOKED,
                    dateTime,
                    LocalDateTime.now(),
                    ""));
        }

    }

    /**
     * Metodo per cambiare lo stato ai ticket corrispondenti alle richieste che non sono state
     * confermate dai pazienti entro i tempi stabiliti. La cancellazione avviene solo se è stato
     * possibile notificare il paziente vie email. Le scadenze sono più prossime per gli
     * appuntamenti in giornata, più lontane per quelli nelle giornate future.
     */
    private void expireBooked() {

        List<TicketData> tickets = dataManager.getTicketsList().stream().filter(ticket -> {

            if (ticket.state == TicketState.BOOKED) {

                boolean isToday = ticket.booking.toLocalDate().equals(LocalDate.now());
                long minutes =
                        ticket.lastInteraction.until(LocalDateTime.now(), ChronoUnit.MINUTES);
                int examDuration = Preferences.examDuration.minutes;

                if ((isToday && (minutes > examDuration)) || minutes > examDuration * 6) {
                    return !sendEmail(ticket.user, "Ticket scaduto: " + ticket.id,
                            StandardEmails.expireBooked(ticket.id));
                }
            }
            return true;
        }).collect(Collectors.toList());

        dataManager.setTicketsList(tickets);

    }

    /**
     * Metodo per avviare il processo di aggiornamento in background. Una volta lanciato il task,
     * esso verrà eseguito ogni 15 secondi, provocando l'aggiornamento del contesto e il conseguente
     * aggiornamento dei client.
     */
    public void startTask() {
        onReload.run();
        shouldUpdate = true;
        int delay = 15;
        task = executor.scheduleAtFixedRate(() -> update(), delay, delay, TimeUnit.SECONDS);
    }

    /**
     * Metodo per arrestare il processo di aggiornamento in background. Eventuali processi pendenti
     * vengono comunque completati.
     */
    public void stopTask() {
        shouldUpdate = false;
        task.cancel(false);
    }

    /**
     * Metodo per inviare un'email, al fine di comunicare con i pazienti. Se l'email viene inviata
     * correttamente restituisce {@code true}, altrimenti {@code false}.
     * 
     * @param userId id dell'utente al quale si vuole inviare l'email
     * @param subject oggetto dell'email
     * @param message corpo dell'email
     * 
     * @return boolean
     */
    private boolean sendEmail(String userId, String subject, String message) {

        Optional<UserData> user = dataManager.getUser(userId);

        if (user.isPresent()) {
            return emailManager.send(new EmailData(user.get().email, subject, message));
        } else {
            throw new UserNotFoundException(userId);
        }
    }


    /**
     * Metodo per inviare un messaggio arbitrario di comunicazione all'utente, l'email così inviata
     * ha di default l'oggetto "comunicazione". Restituisce un booleano che indica se l'invio è
     * andato a buon fine.
     * 
     * @param userId id dell'utente al quale inviare il messaggio
     * @param message messaggio da aggiungere come corpo dell'email
     * @return boolean
     */
    public boolean sendMessage(String userId, String message) {
        boolean isSent = sendEmail(userId, "Comunicazione",
                String.format("<h3>%s</h3><br>%s", message, StandardEmails.COMUNICATION));
        if (isSent) {
            addToChat(userId, message, false);
        }
        return isSent;
    }


    /**
     * Metodo per aggiungere un messaggio alla chat di un utente. Restituisce {@code true} se
     * l'utente è registrato, {@code false} altrimenti.
     * 
     * @param userId id dell'utente di cui si intende aggiornare la chat
     * @param message messaggio da aggiungere alla chat
     * @param isUserSent flag che se vero indica che il messaggio è stato inviato da parte
     *        dell'utente, se falso indica che il messaggio è stato inviato dalla segreteria.
     * @return boolean
     */
    private boolean addToChat(String userId, String message, boolean isUserSent) {
        Optional<UserData> user = dataManager.getUser(userId);
        if (user.isPresent()) {
            List<MessageData> chat = user.get().getChat();
            chat.add(new MessageData(message, LocalDateTime.now(), isUserSent));
            dataManager.setUser(new UserData(
                    user.get().firstName,
                    user.get().lastName,
                    user.get().id,
                    user.get().email,
                    user.get().phone,
                    chat));
        }
        return user.isPresent();
    }

    /**
     * <p>
     * Metodo per modificare un ticket, a patto che l'email di notifica al paziente possa essere
     * inviata. Riceve come oggetto un ticket, se il suo id viene ritrovato aggiorna il ticket,
     * altrimenti se non viene trovato (oppure non c'è la corrispondenza con l'effettivo utente del
     * ticket) solleva un'eccezione.
     * </p>
     * <p>
     * L'oggetto dell'email di notifica dipende dal precedente stato del ticket, può essere di
     * proposta appuntamento o spostamento appuntamento.
     * </p>
     * Aggiorna anche il contenuto della chat per tenere traccia dell'operazione eseguita
     * </p>
     * 
     * @param newTicket ticket caricare
     */
    public void updateTicket(TicketData newTicket) {

        Optional<TicketData> ticket = dataManager.getTicket(newTicket.id);

        if (ticket.isEmpty() || !ticket.get().user.equals(newTicket.user)) {
            throw new TicketNotFoundException(newTicket.id + " and user" + newTicket.user);
        }

        String dateTime[] = AppUtils.localDateTimeToString(newTicket.booking).split(" ");

        final boolean wasAwaiting = ticket.get().state == TicketState.AWAITING;

        boolean isSent = sendEmail(newTicket.user,
                wasAwaiting ? "Proposta appuntamento" : "Spostamento appuntamento",
                StandardEmails.ticketMessage(dateTime[0], dateTime[1], newTicket.id));

        if (isSent) {

            addToChat(newTicket.user, String.format(wasAwaiting
                    ? "Proposta %s - %s %s"
                    : "Spostamento %s - %s %s",
                    newTicket.id, dateTime[0], dateTime[1]),
                    false);

            dataManager.setTicket(newTicket);
        }
    }

    /**
     * Metodo per eliminare un ticket, a patto che l'email di notifica al paziente possa essere
     * inviata. L'oggetto del messaggio di notifica dipende dallo stato del ticket.
     * 
     * @param id id del ticket da eliminare
     */
    public void deleteTicket(String id) {

        Optional<TicketData> ticket = dataManager.getTicket(id);

        if (ticket.isPresent()) {

            boolean isSent;

            if (ticket.get().state == TicketState.AWAITING) {
                isSent = sendEmail(ticket.get().user, "Richiesta cancellata",
                        StandardEmails.NOT_ACCEPTED);
            } else {
                isSent = sendEmail(ticket.get().user, "Appuntamento cancellato",
                        StandardEmails.removeMessage(ticket.get().id));
            }

            if (isSent) {
                addToChat(ticket.get().user,
                        "Richiesta cancellata " + ticket.get().id, false);
                dataManager.deleteTicket(id);
            }

        } else {
            throw new TicketNotFoundException(id);
        }
    }

    /**
     * Metodo per ottenere un ticket dal suo id, incapsulato dentro un optional, questo perché
     * potrebbe non essere presente un ticket con l'id considerato, in questo caso l'optional
     * conterrà empty.
     * 
     * @param id id del ticket da ottenere
     * @return Optional<TicketData>
     */
    public Optional<TicketData> getTicket(String id) {
        return dataManager.getTicket(id);
    }

    /**
     * Metodo per aggiungere un nuovo utente, a patto che l'email di conferma possa essere inviata
     * al paziente.
     * 
     * @param updatedUser
     */
    public boolean addUser(UserData updatedUser) {
        boolean isSent = emailManager.send(new EmailData(
                updatedUser.email,
                "Conferma ed istruzioni",
                StandardEmails.WELCOME));
        if (isSent) {
            dataManager.setUser(updatedUser);
        }
        return isSent;
    }


    /**
     * Metodo per aggiornare le informazioni di un utente, riceve come parametro un oggetto di tipo
     * {@code UserData}, va alla ricerca dell'utente con l'identificativo contenuto all'interno
     * dell'oggetto passato a parametro, e ne aggiorna tutti gli altri campi sempre con il contenuto
     * dell'oggetto passato. I campi lasciati come stringa vuota restano inalterati. L'utente viene
     * notificato dell'operazione ricevendo un'email.
     * 
     * @param updatedUser oggetto utente con i dettagli da aggiornare.
     * @return boolean
     */
    public boolean editUser(UserData updatedUser) {
        Optional<UserData> user = dataManager.getUser(updatedUser.id);

        if (user.isPresent()) {

            UserData temp = new UserData(
                    updatedUser.firstName.isEmpty()
                            ? user.get().firstName
                            : updatedUser.firstName,
                    updatedUser.lastName.isEmpty()
                            ? user.get().lastName
                            : updatedUser.lastName,
                    updatedUser.id,
                    updatedUser.email.isEmpty()
                            ? user.get().email
                            : updatedUser.email,
                    updatedUser.phone.isEmpty()
                            ? user.get().phone
                            : updatedUser.phone,
                    user.get().getChat());

            boolean isSent = emailManager
                    .send(new EmailData(
                            temp.email,
                            "Utente aggiornato",
                            StandardEmails.WELCOME));

            if (isSent) {
                dataManager.setUser(temp);
                return true;
            }
            return false;

        } else {
            throw new UserNotFoundException(updatedUser.id);
        }
    }


    /**
     * Metodo per effettuare la ricerca di un utente tramite la email. Restituisce un optional che
     * contiene l'utente se la ricerca è andata a buon fine, empty altrimenti.
     * 
     * @param email da usare come chiave di ricerca.
     * @return Optional<UserData>
     */
    public Optional<UserData> getUserByEmail(String email) {
        return dataManager.getUserByEmail(email);
    }


    /**
     * Metodo per ottenere un utente dal suo id. Restituisce un optional che contiene l'utente se la
     * ricerca è andata a buon fine, empty altrimenti.
     * 
     * @param id id dell'utente da ottenere
     * @return Optional<UserData>
     */
    public Optional<UserData> getUser(String id) {
        return dataManager.getUser(id);
    }


    /**
     * Metodo che restituisce la lista di utenti i cui attributi corrispondono ai parametri di
     * ricerca contenuti all'interno dell'oggetto passato come parametro. Il metodo restituisce gli
     * utenti che soddisfano contemporaneamente tutte le chiavi di ricerca. È possibile omettere un
     * campo di ricerca inserendo una stringa vuota.
     * 
     * @param user oggetto con le chiavi di ricerca
     * @return List<UserData> risultato della ricerca
     */
    public List<UserData> searchUsers(UserData user) {
        return dataManager.searchUsers(user);
    }

    /**
     * Metodo per aggiungere una {@code Procedure} che sarà chiamata in seguito al riavvio del task
     * in background. Permette ai client di ContextManager di essere notificati in seguito al
     * riavvio del task in background.
     * 
     * @param onReload
     * @return ContextManager
     */
    public ContextManager addOnReload(Procedure onReload) {
        this.onReload = onReload;
        return this;
    }


    /**
     * Metodo per aggiungere una {@code Procedure} che sarà chiamata in seguito al verificarsi di un
     * aggiornamento. Permette ai client di ContextManager di essere notificati in seguito
     * all'avvenimento di un update lanciato dal task in background.
     * 
     * @param onUpdate
     * @return ContextManager
     */
    public ContextManager addOnUpdate(Procedure onUpdate) {
        this.onUpdate = onUpdate;
        return this;
    }

    /**
     * Metodo per ottenere la lista dei ticket in stato d'attesa.
     * 
     * @return List<TicketData>
     */
    public List<TicketData> getAwaitingTickets() {
        return dataManager.getTicketsList().stream()
                .filter(ticket -> ticket.state == TicketState.AWAITING)
                .collect(Collectors.toList());
    }


    /**
     * Metodo per restituire l'eventuale ticket in attesa di essere gestito.
     * 
     * @param userId
     * @return Optional<TicketData>
     */
    public Optional<TicketData> getPendingTicketForUser(String userId) {
        return dataManager.getTicketsList().stream().filter(
                ticket -> ticket.user.equals(userId) && ticket.state != TicketState.CONFIRMED)
                .findFirst();
    }


    /**
     * Metodo per ottenere la lista di tutti gli utenti
     * 
     * @return List<UserData>
     */
    public List<UserData> getUsers() {
        return dataManager.getUsersList();
    }


    /**
     * Metodo per ottenere la lista delle prenotazioni a partire dalla data passata come parametro,
     * in ordine cronologico.
     * 
     * @param date
     * @return List<TicketData>
     */
    public List<TicketData> getReservationsStartFromDate(LocalDate date) {
        return dataManager.getTicketsList().stream().filter(ticket -> {
            return ((ticket.state != TicketState.AWAITING)
                    && (ticket.booking.toLocalDate().equals(date)
                            || ticket.booking.toLocalDate().isAfter(date)));
        }).sorted((ticket1, ticket2) -> ticket1.booking.compareTo(ticket2.booking))
                .collect(Collectors.toList());
    }

    /**
     * Metodo per ottenere la lista delle prenotazioni per una data giornata.
     * 
     * @param date data per la quale si vuole ottenere la lista delle prenotazioni
     * @return List<TicketData>
     */
    public List<TicketData> getReservationsForDate(LocalDate date) {
        return dataManager.getTicketsList().stream().filter(ticket -> {
            return (ticket.state != TicketState.AWAITING)
                    && ticket.booking.toLocalDate().equals(date);
        }).sorted((ticket1, ticket2) -> ticket1.booking.compareTo(ticket2.booking))
                .collect(Collectors.toList());
    }

    /**
     * Metodo per ricavare la prima coppia di data e ora libera per fissare un nuovo appuntamento.
     * 
     * @return LocalDateTime
     */
    public LocalDateTime firstAvailableReservation() {

        LocalTime opening = Preferences.openingTime;
        LocalTime closing = Preferences.closingTime;

        int examDuration = Preferences.examDuration.minutes;

        LocalTime last = closing.minusMinutes(examDuration);

        LocalDateTime candidate = LocalDateTime.now();

        if (candidate.toLocalTime().isBefore(opening)) {
            candidate = candidate.toLocalDate().atTime(opening.minusMinutes(examDuration));
        }

        int minutes = (int) (examDuration
                * (Math.ceil(candidate.getMinute() / ((double) examDuration)) + 1));

        candidate = candidate.truncatedTo(ChronoUnit.MINUTES).withMinute(0).plusMinutes(minutes);
        candidate = dataManager.getTicketsList().stream().map(ticket -> ticket.booking).sorted()
                .reduce(candidate,
                        (prev, curr) -> {
                            if (prev.toLocalTime().isAfter(last)) {
                                prev = prev.toLocalDate().plusDays(1).atTime(opening);
                            }
                            if (prev.equals(curr)) {
                                prev = prev.plusMinutes(examDuration);
                            }
                            return prev;
                        });
        return candidate;
    }

    /**
     * Metodo che verifica se una data coppia data e ora sia valida per fissare un appuntamento, se
     * ricade in uno slot libero restituisce vero, altrimenti falso.
     * 
     * @param candidateDateTime
     * @return boolean
     */
    public boolean isValidReservation(LocalDateTime candidateDateTime) {
        return dataManager.getTicketsList().stream()
                .filter(ticket -> candidateDateTime.equals(ticket.booking))
                .collect(Collectors.toList()).isEmpty();
    }
}
