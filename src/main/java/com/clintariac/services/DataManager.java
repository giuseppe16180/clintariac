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
import javax.mail.Message;
import com.clintariac.data.MessageData;
import com.clintariac.data.TicketData;
import com.clintariac.data.TicketState;
import com.clintariac.data.UserData;
import com.clintariac.services.utils.ListNotLoadedException;
import com.clintariac.services.utils.SingletonException;
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
     * @param onException
     */
    public void addOnException(Consumer<Exception> onException) {
        this.onException = onException;
    }

    /*****************************
     * TICKETS
     *****************************/
    /**
     * Metodo per leggere un file json, grazie alla libreria GSON, ottendendo la lista di tutti i
     * ticket.
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
     * Metodo per memorizzare su il file json la lista di ticket, quindi aggiornandolo in caso di
     * una modifica alla lista.
     */
    public void storeTicketsList() {
        // System.out.println("store"); todo: capire perchE viene sempre chiamato
        assertTicketsList();

        try (Writer writer = new FileWriter(ticketsFile)) {

            json.toJson(ticketsList, writer);

        } catch (IOException e) {
            onException.accept(e);
        }
    }

    /**
     * Metodo per aggiornare i dati di un ticket, se l'identificativo del ticket è presente nella
     * lista, o altrimenti inserisce il nuovo ticket alla fine della lista. Aggiornata la lista, la
     * si memorizza su file.
     * 
     * @param newTicket singolo ticket
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
     * Metodo per aggiornare, tramite una lista passata a parametro, la lista di ticket e la
     * memorizzarla su file.
     * 
     * @param tickets nuova lista di ticket
     */
    public void setTicketsList(List<TicketData> tickets) {

        assertTicketsList();
        ticketsList = List.copyOf(tickets);
        storeTicketsList();
    }

    /**
     * Metodo per cancellare dalla lista un ticket il cui identificativo è passato a parametro, se
     * essa non è nulla. La lista modificata viene memorizzata su file.
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
     * Metodo per concatenare una lista di ticket alla già presente lista e quindi memorizzare la
     * lista di ticket completa su file.
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
     * Metodo che restituisce una copia della lista di ticket, se non è vuota.
     * 
     * @return List<TicketData>
     */
    public List<TicketData> getTicketsList() {

        assertTicketsList();
        return List.copyOf(ticketsList);
    }

    /**
     * Metodo che restituisce {@code Optional<TicketData>} effettuando la ricerca nella lista
     * tramite l'identificativo. Se il ticket non è presente nella lista, il metodo restituisce
     * {@code null}.
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
     * Metodo per leggere un file json, grazie alla libreria GSON, ottendendo la lista di utenti
     * registrati al sistema.
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
     * Metodo per memorizzare nel file json la lista di utenti registrati.
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
     * dati del nuovo utente alla fine della lista. Aggiornata la lista, la si memorizza su file.
     * 
     * @param newUser
     */
    public void setUser(UserData newUser) {

        assertUsersList();

        if (getUser(newUser.id).isPresent()) {

            usersList = usersList.stream().map(user -> {
                if (user.id.equals(newUser.id)) {
                    UserData temp = new UserData(
                            newUser.firstName.isEmpty() ? user.firstName : newUser.firstName,
                            newUser.lastName.isEmpty() ? user.lastName : newUser.lastName,
                            newUser.id,
                            newUser.email.isEmpty() ? user.email : newUser.email,
                            newUser.phone.isEmpty() ? user.phone : newUser.phone,
                            newUser.getChat());
                    return temp;
                }
                return user;
            }).collect(Collectors.toList());
        }

        else {
            usersList = Stream.concat(usersList.stream(), Stream.of(newUser))
                    .collect(Collectors.toList());
        }

        storeUsersList();
    }

    /**
     * Metodo che restituisce una copia della lista degli utenti registrati, se essa non è vuota.
     * 
     * @return List<UserData>
     */
    public List<UserData> getUsersList() {

        assertUsersList();

        return List.copyOf(usersList);
    }

    /**
     * Metodo per effettuare la ricerca di un utente tramite identificativo dell'utente, se la lista
     * non è vuota.
     * 
     * @param id dell'utente che si vuole cercare
     * @return Optional<UserData>
     */
    public Optional<UserData> getUser(String id) {

        assertUsersList();

        return usersList.stream().filter(user -> user.id.equals(id)).findFirst();
    }

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
     * Metodo che lancia un'eccezione nel caso la lista ticket sia vuota.
     */
    private void assertTicketsList() {

        if (ticketsList == null) {
            throw new ListNotLoadedException("ticketsList not loaded");
        }
    }

    /**
     * Metodo per effettuare la ricerca di un utente tramite la email, se la lista non è vuota. todo
     * 
     * @param email dell'utente che si vuole cercare
     * @return Optional<UserData>
     */

    public Optional<UserData> getUserByEmail(String email) {

        assertUsersList();

        return usersList.stream().filter(user -> user.email.equals(email)).findFirst();
    }

    /**
     * Metodo che lancia un'eccezione nel caso la lista utenti sia vuota.
     */
    private void assertUsersList() {

        if (usersList == null) {
            throw new ListNotLoadedException("usersList not loaded");
        }
    }
}
