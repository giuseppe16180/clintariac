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
     * una casella di posta elettronica, è necessario che non possano esisterne più istanze. In caso
     * di tentata istanziazione multipla, viene lanciata un'eccezione.
     * </p>
     * 
     * <p>
     * Permette di leggere e scrivere sulle liste dei ticket e degli user, in maniera persistente
     * tra i vari avvii dell'applicazione. Contiene i vari metodi per alterare le strutture dati in
     * maniera limitata a quelle che dovrebbero essere le interazioni scaturite dall'interfaccia
     * utente.
     * </p>
     * 
     * <p>
     * Sulla base delle interazioni effettuate sulle strutture dati, invia delle email verso gli
     * utenti interessati, per comuniucare modifiche ai loro appuntamenti, o per comunicare le
     * istruzioni in seguito alla loro registrazione.
     * </p>
     * 
     * <p>
     * Permette di configurare un processo eseguito ciclicamente in background per gestire
     * l'aggiornamento dell'applicatico, in seguito alla ricezione di nuove email.
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
     * Metodo che permette di caricare in memoria le strutture dati, partendo dal contentuto
     * precedentemente scritto in memoria secondaria. Deve essere chiamato prima di procedere alle
     * operazioni sui dati.
     * </p>
     * 
     * <p>
     * In seguito al caricamento viene lanciato anche l'aggiornamento della lista di ticket, con il
     * pull delle nuove email, e l'eliminazione delle prenotazioni in attesa e scadute.
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
     * @param onException eccezione lanciata
     */
    public void addOnDataException(Consumer<Exception> onException) {
        dataManager.addOnException(onException);
    }

    /**
     * Metodo per aggiungere un evento in caso di eccezione in fase di invio o di ricezione delle
     * email.
     * 
     * @param onException eccezione lanciata
     */
    public void addOnEmailException(Consumer<Exception> onException) {
        emailManager.addOnException(onException);
    }

    /**
     * Metodo per inizializzare {@code emailHandler}.
     * 
     * <p>
     * Predispone una serie di {@code Predicate<EmailData>} e {@code Consumer<EmailData>} per poter
     * realizzare poi una chain of responsability, capace di gestire in maniera esaustiva le
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
            emailManager.send(new EmailData(email.address, "Utente non registrato",
                    StandardEmails.UNREGISTERED));
        };

        final Predicate<EmailData> isNewMessage =
                email -> email.subject.toLowerCase().contains(comunToken);

        final Consumer<EmailData> consumeNewMessage = email -> {

            UserData user = dataManager.getUserByEmail(email.address).get();
            List<MessageData> chat = user.getChat();
            chat.add(new MessageData(email.message, LocalDateTime.now(), true));
            dataManager.updateChat(user.id, chat);

            Optional<TicketData> oldWaitingTicket = dataManager.getTicketsList().stream()
                    .filter(ticket -> ticket.user.equals(user.id)
                            && ticket.state == TicketState.AWAITING)
                    .findFirst();

            if (oldWaitingTicket.isPresent()) {
                dataManager.deleteTicket(oldWaitingTicket.get().id);
            }

            String newTicketId = Integer
                    .toString(dataManager.getTicketsList().stream()
                            .map(ticket -> Integer.parseInt(ticket.id))
                            .reduce(1000, (acc, curr) -> curr > acc ? curr : acc) + 1);

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
            emailManager.send(new EmailData(email.address, "È stato riscontrato un errore",
                    StandardEmails.ERROR));
        };

        final Predicate<EmailData> isNotValidTicket =
                email -> !dataManager.getTicket(email.message.split("\\s+")[0])
                        .isPresent();

        final Consumer<EmailData> consumeNotValidTicket = email -> {
            emailManager.send(
                    new EmailData(email.address, "Ticket non valido", StandardEmails.NOT_VALID));
        };

        final Predicate<EmailData> isConfirmTicket =
                email -> email.subject.toLowerCase().equals(confirmToken);

        final Consumer<EmailData> consumeConfirmTicket = email -> {

            TicketData ticket = dataManager.getTicket(email.message.split("\\s+")[0]).get();

            String dateTime[] = AppUtils.localDateTimeToString(ticket.booking).split(" ");

            boolean isSent = emailManager.send(new EmailData(email.address, "Conferma appuntamento",
                    StandardEmails.confirmMessage(dateTime[0], dateTime[1], ticket.id)));

            if (isSent) {
                addToChat(ticket.user, "Conferma", true);

                dataManager.setTicket(new TicketData(ticket.id, ticket.user, TicketState.CONFIRMED,
                        ticket.booking,
                        LocalDateTime.now(), ticket.message));
            }
        };

        final Predicate<EmailData> isDeleteTicket =
                email -> email.subject.toLowerCase().equals(deleteToken);

        final Consumer<EmailData> consumeDeleteTicket = email -> {

            TicketData ticket = dataManager.getTicket(email.message.split("\\s+")[0]).get();

            boolean isSent = emailManager.send(
                    new EmailData(email.address, "Appuntamento cancellato",
                            StandardEmails.deleteMessage(ticket.id)));

            if (isSent) {
                addToChat(ticket.user, "Annulla", true);
                dataManager.deleteTicket(ticket.id);
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
     * <li>eliminazione le richieste di prenotazione</li> scadute.
     * </ul>
     * <p>
     * Dato che questo aggiornamento è parte dell'inizializzazione del context, oppure potrebbe
     * essere eseguito da un task in pendente in background (è stata richiesta la sospensione del
     * lancio di nuovi task ma ne è presente già uno avviato), il client non sempre viene notificato
     * dell'aggiornamento.
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
     * Metodo per eliminare le proposte di prenotazione che non sono state confermate dai pazienti
     * entro i tempi stabiliti. Se è possibile notificare il paziente circa la cancellazione, il
     * ticket viene cancellato. Le scadenze sono minori per gli appuntamenti in giornata, maggiori
     * per quelli futuri.
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
     * Metodo per avviare il processo di aggiornamento in background.
     */
    public void startTask() {
        onReload.run();
        shouldUpdate = true;
        int delay = 15;
        task = executor.scheduleAtFixedRate(() -> update(), delay, delay, TimeUnit.SECONDS);
    }

    /**
     * Metodo arrestare il processo di aggiornamento in background. Eventuali processi pendenti
     * vengono comunque completati.
     */
    public void stopTask() {
        shouldUpdate = false;
        task.cancel(false);
    }

    /**
     * Metodo per inviare un'email, al fine di comunicare con i pazienti. Se l'email viene inviata
     * correttamente resetituisce {@code true}, altrimenti {@code false}.
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

    public boolean sendMessage(String userId, String message) {
        boolean isSent = sendEmail(userId, "Comunicazione",
                String.format("<h3>%s</h3><br>%s", message, StandardEmails.COMUNICATION));
        if (isSent) {
            addToChat(userId, message, false);
        }
        return isSent;
    }

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
     * Metodo per modificare un ticket, a patto che l'email di notifica al paziente possa essere
     * inviata
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
                    ? "Proposta appuntamento - %s %s"
                    : "Spostamento appuntamento - %s %s",
                    dateTime[0], dateTime[1]),
                    false);

            dataManager.setTicket(newTicket);
        }
    }

    /**
     * Metodo per eliminare un ticket, a patto che l'email di notifica al paziente possa essere
     * inviata
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
                addToChat(ticket.get().user, "Appuntamento cancellato", false);
                dataManager.deleteTicket(id);
            }

        } else {
            throw new TicketNotFoundException(id);
        }
    }

    /**
     * Metodo per otteere un ticket dal suo id. Resituisce un optional perché potrebbe non essere
     * presente un ticket con l'id considerato.
     * 
     * @param id id del ticket da ottenere
     * @return Optional<TicketData>
     */
    public Optional<TicketData> getTicket(String id) {
        return dataManager.getTicket(id);
    }

    /**
     * Metodo per aggiungere un nuovo utente. a patto che l'email con le istruzioni al paziente
     * possa essere inviata
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

    public Optional<UserData> getUserByEmail(String email) {
        return dataManager.getUserByEmail(email);
    }


    /**
     * Metodo per ottenere un utente dal suo id. Resituisce un optional perché potrebbe non essere
     * presente un utente con l'id considerato.
     * 
     * @param id id dell'utente da ottenere
     * @return Optional<UserData>
     */
    public Optional<UserData> getUser(String id) {
        return dataManager.getUser(id);
    }

    public List<UserData> searchUsers(UserData user) {
        return dataManager.searchUsers(user);
    }

    /**
     * Metodo per aggiungere una Procedure che sarà chiamata in seguito al verificarsi di un
     * aggiornamento. Permette ad i client di ContextManager di essere notificati in seguto
     * all'avvenimento di un update lanciato dal task in background.
     * 
     * @param onUpdate
     * @return ContextManager
     */
    public ContextManager addOnReload(Procedure onReload) {
        this.onReload = onReload;
        return this;
    }

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

    public Optional<TicketData> getPendingTicketForUser(String userId) {
        return dataManager.getTicketsList().stream().filter(
                ticket -> ticket.user.equals(userId) && ticket.state != TicketState.CONFIRMED)
                .findFirst();
    }

    public List<UserData> getUsers() {
        return dataManager.getUsersList().stream().collect(Collectors.toList());
    }

    public List<TicketData> getReservationsStartFromDate(LocalDate date) {
        return dataManager.getTicketsList().stream().filter(ticket -> {
            return ((ticket.state != TicketState.AWAITING)
                    && (ticket.booking.toLocalDate().equals(date)
                            || ticket.booking.toLocalDate().isAfter(date)));
        }).sorted((ticket1, ticket2) -> ticket1.booking.compareTo(ticket2.booking))
                .collect(Collectors.toList());
    }

    /**
     * Metodo per ottenere la lista delle prenotazioni confermate o in attesa di riscontro per una
     * data giornata.
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
     * Metodo per ricavare le prime data e ora libere per fissare un nuovo appuntamento.
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
     * @param candidateDateTime
     * @return boolean
     */
    public boolean isValidReservation(LocalDateTime candidateDateTime) {
        return dataManager.getTicketsList().stream()
                .filter(ticket -> candidateDateTime.equals(ticket.booking))
                .collect(Collectors.toList()).isEmpty();
    }
}
