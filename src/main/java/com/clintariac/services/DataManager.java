package com.clintariac.services;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.clintariac.data.MessageData;
import com.clintariac.data.TicketData;
import com.clintariac.data.TicketState;
import com.clintariac.data.UserData;
import com.clintariac.services.utils.ListNotLoadedException;
import com.clintariac.services.utils.SingletonException;
import com.clintariac.services.utils.UserNotFoundException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class DataManager {

    private static boolean isInstantiated = false;

    private File ticketsFile;
    private File usersFile;

    private Gson json;

    private List<TicketData> ticketsList;
    private List<UserData> usersList;

    private Consumer<Exception> onException;

    /**
     * <p>
     * Il DataManager è un singleton per la gestione persistente dello stato dell'applicativo. Offre
     * vari metodi per interagire con le collezioni. In caso di tentata instanziazione multipla
     * solleva una {@code SingletonException}.
     * </p>
     */

    public DataManager() {

        if (DataManager.isInstantiated == true) {
            throw new SingletonException();
        } else {

            this.ticketsFile = new File("tickets.json");
            this.usersFile = new File("users.json");

            json = new Gson();

            isInstantiated = true;
            onException = e -> {
                System.exit(0);
            };
        }
    }

    /**
     * <p>
     * Metodo per aggiungere una funzione da richiamare in caso di avvenuta eccezione nelle
     * operazioni di gestione file.
     * </p>
     * 
     * @param onException consumer di exception che definisce cosa fare con l'eccezione sollevata.
     */
    public void addOnException(Consumer<Exception> onException) {
        this.onException = onException;
    }

    /*****************************
     * TICKETS
     *****************************/
    /**
     * Metodo per leggere un file json, grazie alla libreria GSON. Permette di caricare in memoria
     * la lista di tutti i ticket presenti su file in formato json.
     */
    public void loadTicketsList() {

        this.ticketsList = new ArrayList<>();

        try {
            if (!ticketsFile.createNewFile()) {
                Reader reader = new FileReader(ticketsFile);
                ticketsList = json.fromJson(reader,
                        TypeToken.getParameterized(List.class, TicketData.class).getType());
                reader.close();
            } else {
                storeTicketsList();
            }
        } catch (IOException e) {
            onException.accept(e);
        }
    }

    /**
     * Metodo per memorizzare su file json la lista di ticket, questo metodo aggiorna il file con
     * l'attuale lista dei ticket.
     */
    public void storeTicketsList() {

        assertTicketsList();

        try (Writer writer = new FileWriter(ticketsFile)) {

            json.toJson(ticketsList, writer);

        } catch (IOException e) {
            onException.accept(e);
        }
    }

    /**
     * Metodo per aggiornare i dati di un ticket se l'identificativo del ticket è presente nella
     * lista, altrimenti inserisce il nuovo ticket alla fine della lista. Aggiornata la lista, viene
     * scritta su file.
     * 
     * @param newTicket singolo ticket con cui aggiornare la lista
     */
    public void setTicket(TicketData newTicket) {

        assertTicketsList();

        if (getTicket(newTicket.id).isPresent()) {

            ticketsList = ticketsList.stream()
                    .map(ticket -> ticket.id.equals(newTicket.id) ? newTicket : ticket)
                    .collect(Collectors.toList());
        }

        else {
            ticketsList = Stream.concat(ticketsList.stream(), Stream.of(newTicket))
                    .collect(Collectors.toList());
        }

        storeTicketsList();
    }

    /**
     * Metodo per sovrascrivere la lista di ticket con una lista passata a parametro. Dopo
     * l'aggiornamento memorizza la lista su file.
     * 
     * @param tickets nuova lista di ticket
     */
    public void setTicketsList(List<TicketData> tickets) {

        assertTicketsList();
        ticketsList = List.copyOf(tickets);
        storeTicketsList();
    }

    /**
     * Metodo per cancellare dalla lista un ticket il cui identificativo è passato a parametro.
     * Verifica che la lista sia stata caricata. La lista modificata viene memorizzata su file.
     * 
     * @param id identificativo del ticket
     */
    public void deleteTicket(String id) {

        assertTicketsList();

        ticketsList = ticketsList.stream()
                .filter(Predicate.not(
                        ticket -> ticket.id.equals(id) && ticket.state == TicketState.AWAITING))
                .map(ticket -> {
                    return ticket.id.equals(id)
                            ? new TicketData(ticket.id, ticket.user, TicketState.DELETED,
                                    ticket.booking,
                                    ticket.lastInteraction, ticket.message)
                            : ticket;
                }).collect(Collectors.toList());

        storeTicketsList();
    }

    /**
     * Metodo per concatenare una lista di ticket alla già presente lista. La lista modificata viene
     * memorizzata su file.
     * 
     * @param newTickets la lista di ticket che si vuole concatenare
     */
    public void appendTicketsList(List<TicketData> newTickets) {

        assertTicketsList();

        ticketsList = Stream.concat(ticketsList.stream(), newTickets.stream())
                .collect(Collectors.toList());

        storeTicketsList();
    }

    /**
     * Metodo che restituisce una copia della lista di ticket. Verifica che la lista sia stata
     * caricata.
     * 
     * @return List<TicketData>
     */
    public List<TicketData> getTicketsList() {

        assertTicketsList();
        return List.copyOf(ticketsList);
    }

    /**
     * Metodo che restituisce {@code Optional<TicketData>} effettuando la ricerca nella lista
     * tramite l'identificativo. Se il ticket è presente nella lista verrà restituito dentro
     * l'{@code optional}, altrimenti il metodo restituisce un {@code optional} vuoto.
     * 
     * @param id identificativo del ticket
     * @return Optional<TicketData>
     */
    public Optional<TicketData> getTicket(String id) {

        assertTicketsList();
        return ticketsList.stream().filter(ticket -> ticket.id.equals(id)).findFirst();
    }

    /*****************************
     * USERS
     *****************************/
    /**
     * Metodo per leggere un file json, grazie alla libreria GSON. Permette di caricare in memoria
     * la lista di tutti gli utenti presenti su file in formato json.
     */
    public void loadUsersList() {

        this.usersList = new ArrayList<>();

        try {
            if (!usersFile.createNewFile()) {
                Reader reader = new FileReader(usersFile);
                usersList = json.fromJson(reader,
                        TypeToken.getParameterized(List.class, UserData.class).getType());
                reader.close();
            } else {
                storeUsersList();
            }
        } catch (IOException e) {
            onException.accept(e);
        }
    }

    /**
     * Metodo per memorizzare nel file json la lista di utenti registrati. Verifica che la lista sia
     * stata precedentemente caricata.
     */
    public void storeUsersList() {

        assertUsersList();

        try (Writer writer = new FileWriter(usersFile)) {

            json.toJson(usersList, writer);

        } catch (IOException e) {
            onException.accept(e);
        }
    }

    /**
     * Metodo per aggiornare i dati di un utente se registrato al sistema, altrimenti inserisce i
     * dati del nuovo utente alla fine della lista. Dopo l'aggiornamento memorizza la lista su file.
     * 
     * @param newUser
     */
    public void setUser(UserData newUser) {

        assertUsersList();

        if (getUser(newUser.id).isPresent()) {

            usersList = usersList.stream().map(user -> user.id.equals(newUser.id) ? newUser : user)
                    .collect(Collectors.toList());
        }

        else {
            usersList = Stream.concat(usersList.stream(), Stream.of(newUser))
                    .collect(Collectors.toList());
        }

        storeUsersList();
    }


    /**
     * Metodo per aggiornare la chat di un utente, sovrascrive la precedente lista di messaggi con
     * la nuova lista fornita come parametro. In caso di utente non presente solleva un'eccezione.
     * 
     * @param userId id dell'utente di cui aggiornare la chat
     * @param chat messaggi con cui sovrascrivere la chat
     */
    public void updateChat(String userId, List<MessageData> chat) {

        Optional<UserData> user = getUser(userId);

        if (user.isPresent()) {
            setUser(new UserData(
                    user.get().firstName,
                    user.get().lastName,
                    user.get().id,
                    user.get().email,
                    user.get().phone,
                    chat));
        } else {
            throw new UserNotFoundException(userId);
        }
    }

    /**
     * Metodo che restituisce una copia della lista degli utenti registrati. Verifica che la lista
     * sia stata precedentemente caricata.
     * 
     * @return List<UserData>
     */
    public List<UserData> getUsersList() {

        assertUsersList();

        return List.copyOf(usersList);
    }

    /**
     * Metodo per effettuare la ricerca di un utente tramite il suo identificativo. Verifica che la
     * lista sia stata precedentemente caricata.
     * 
     * @param id dell'utente da ricercare
     * @return Optional<UserData> optional con il risultato della ricerca.
     */
    public Optional<UserData> getUser(String id) {

        assertUsersList();

        return usersList.stream().filter(user -> user.id.equals(id)).findFirst();
    }


    /**
     * Metodo che restituisce la lista di utenti i cui attributi corrispondono ai parametri di
     * ricerca contenuti all'interno dell'oggetto passato come parametro. Il metodo restituisce gli
     * utenti che soddisfano contemporaneamente tutte le chiavi di ricerca. È possibile omettere un
     * campo di ricerca inserendo una stringa vuota.
     * 
     * @param userToSearch oggetto con le chiavi di ricerca
     * @return List<UserData> risultato della ricerca
     */
    public List<UserData> searchUsers(UserData userToSearch) {

        assertUsersList();

        return usersList.stream()
                .filter(user -> userToSearch.id.equals("")
                        || userToSearch.id.equalsIgnoreCase(user.id))
                .filter(user -> userToSearch.firstName.equals("")
                        || userToSearch.firstName.equalsIgnoreCase(user.firstName))
                .filter(user -> userToSearch.lastName.equals("")
                        || userToSearch.lastName.equalsIgnoreCase(user.lastName))
                .filter(user -> userToSearch.email.equals("")
                        || userToSearch.email.equalsIgnoreCase(user.email))
                .filter(user -> userToSearch.phone.equals("")
                        || userToSearch.phone.equalsIgnoreCase(user.phone))
                .collect(Collectors.toList());
    }

    /**
     * Metodo che lancia un'eccezione nel caso in cui la lista ticket sia nulla.
     */
    private void assertTicketsList() {

        if (ticketsList == null) {
            throw new ListNotLoadedException("ticketsList not loaded");
        }
    }

    /**
     * Metodo per effettuare la ricerca di un utente tramite la email. Verifica che la lista sia
     * stata precedentemente caricata.
     * 
     * @param email dell'utente che si vuole ricercare
     * @return Optional<UserData>
     */

    public Optional<UserData> getUserByEmail(String email) {

        assertUsersList();

        return usersList.stream().filter(user -> user.email.equals(email)).findFirst();
    }

    /**
     * Metodo che lancia un'eccezione nel caso in cui la lista utenti sia nulla.
     */
    private void assertUsersList() {

        if (usersList == null) {
            throw new ListNotLoadedException("usersList not loaded");
        }
    }


    /**
     * Metodo che restituisce il primo id valido per un nuovo ticket
     * 
     * @return String
     */
    public String newTicketId() {
        return Integer.toString(ticketsList.stream()
                .map(ticket -> Integer.parseInt(ticket.id))
                .reduce(1000, (acc, curr) -> curr > acc ? curr : acc) + 1);

    }
}
