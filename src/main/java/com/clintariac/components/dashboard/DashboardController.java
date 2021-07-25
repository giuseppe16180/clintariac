package com.clintariac.components.dashboard;

import java.awt.Component;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.JOptionPane;
import com.clintariac.components.calendar.CalendarController;
import com.clintariac.components.details.DetailsController;
import com.clintariac.components.details.DetailsModel;
import com.clintariac.components.mvc.Controller;
import com.clintariac.components.optionBar.OptionBarController;
import com.clintariac.components.patients.PatientsController;
import com.clintariac.components.reservationsList.reservation.ReservationModel;
import com.clintariac.components.ticketsList.TicketsListController;
import com.clintariac.components.ticketsList.TicketsListModel;
import com.clintariac.components.ticketsList.ticket.TicketModel;
import com.clintariac.components.userList.UsersListController;
import com.clintariac.components.userList.UsersListModel;
import com.clintariac.components.userList.user.UserModel;
import com.clintariac.components.reservationsList.ReservationsListController;
import com.clintariac.components.reservationsList.ReservationsListModel;
import com.clintariac.data.TicketData;
import com.clintariac.data.TicketState;
import com.clintariac.data.UserData;
import com.clintariac.services.ContextManager;
import com.clintariac.services.utils.AppUtils;

/**
 * DashboardController
 * 
 * Classe controller predisposta a gestire l'interazione tra i vari controller
 * dell'applicativo.
 */

public class DashboardController implements Controller {

	private DashboardModel model;
	private DashboardView view;

	private ContextManager context;

	private CalendarController calendar;
	private ReservationsListController resList;
	private DetailsController details;
	private PatientsController patient;
	private UsersListController usersList;
	private TicketsListController ticketsList;
	private OptionBarController optionBar;

	/**
	 * Costruttore di DashboardController, instanzia model e view della Dashbord,
	 * dopodiche' instanza il ContextManager, recupera i riferimenti ad i controller
	 * dichiarati dentro la DashboardView, per poi aggiungere i vari eventi, e i
	 * supplier. Questi supplier effettuano il parsing dei data dal context nei vari
	 * model. In questo modo, in fase di update, ciascuna view sara' ricaricata
	 * opportunamente con il model aggiornato.
	 */

	private enum Senders {
		TICKETS_LIST, RES_LIST
	}

	public DashboardController() {

		model = new DashboardModel();
		view = new DashboardView();

		context = new ContextManager();

		calendar = view.getCalendarController();
		calendar.addOnDateSelect(this::dateSelect);
		calendar.addAllSelect(this::allDateSelect);

		resList = view.getReservationsListController();
		// todo: sistemare questi nomi, sono ambigui, parliamo di ticket, restituiamo un
		// user
		resList.addOnTicketSelect((ticketId, userId) -> {
			ticketSelect(Senders.RES_LIST, ticketId, userId);
		});

		resList.setModelSupplier(() -> {
			return new ReservationsListModel(
					context.getReservationsForDate(model.getSelectedDate()).stream().map(ticket -> {
						UserData user = context.getUser(ticket.user).get();
						return new ReservationModel(user.firstName + " " + user.lastName,
								AppUtils.localDateTimeToString(ticket.booking), ticket.user, ticket.id, ticket.state);
					}).collect(Collectors.toList()));
		});

		ticketsList = view.getTicketsListController();
		ticketsList.addOnTicketSelect((ticketId, userId) -> ticketSelect(Senders.TICKETS_LIST, ticketId, userId));
		ticketsList.setModelSupplier(() -> {
			return new TicketsListModel(context.getAwaitingTickets().stream().map(ticket -> {
				UserData user = context.getUser(ticket.user).get();
				return new TicketModel(user.firstName + " " + user.lastName, ticket.message,
						AppUtils.localDateTimeToString(ticket.lastInteraction), ticket.user, ticket.id);
			}).collect(Collectors.toList()));
		});

		usersList = view.getUsersListController();
		usersList.addOnUserSelect(id -> userSelect(id));
		usersList.setModelSupplier(() -> {
			return new UsersListModel(context.getUsers().stream().map(user -> {
				return new UserModel(user.firstName + " " + user.lastName, user.id);
			}).collect(Collectors.toList()));
		});

		details = view.getDetailsController();
		details.addOnSave(this::detailsSave);
		details.addOnSend(this::sendMessage);
		details.addOnValidate(this::detailsValidate);
		details.addOnDelete(this::detailsDelete);

		details.setModelSupplier(() -> {
			if (model.isUserSelected()) {
				// todo: il context dovrebbe predisporre questo metodo
				Optional<TicketData> awaitingTicket = context.getTicketsList().stream().filter(
						ticket -> ticket.user.equals(model.getSelectedUser()) && ticket.state == TicketState.AWAITING)
						.findFirst();
				if (awaitingTicket.isPresent()) {
					TicketData ticket = awaitingTicket.get(); // toccato
					UserData user = context.getUser(ticket.user).get();
					model.setSelectedTicket(ticket.id);
					return new DetailsModel.Builder().withUserId(ticket.user).withFirstName(user.firstName)
							.withLastName(user.lastName).withEmail(user.email).withPhone(user.phone).withAwaiting(true)
							.withDateTime(context.firstAvailableReservation()).withTicketId(ticket.id)
							.withChat(user.getChat()).build();
				} else {
					UserData user = context.getUser(model.getSelectedUser()).get();
					model.setSelectedUser(user.id);
					return new DetailsModel.Builder().withUserId(user.id).withFirstName(user.firstName)
							.withLastName(user.lastName).withEmail(user.email).withPhone(user.phone).withAwaiting(false)
							.withChat(user.getChat()).withTicketId(model.getSelectedTicket()).build();
				}
			} else {
				return DetailsModel.empty();
			}
		});

		patient = view.getPatientsController();
		patient.addOnSave(this::patientSave);
		patient.addOnSearch(this::patientSearch);

		optionBar = view.getOptionBarController();
		optionBar.addOnReload(this::reload);

		context.addOnDataException(this::dataException);
		context.addOnEmailException(this::emailException);

		context.addOnUpdate(this::contextUpdate);
		context.loadData();

		Stream.of(ticketsList, resList, usersList).forEach(Controller::updateView);

		context.startTask();
	}

	/**
	 * Metodo per ricaricare le view del controller, quando invocato ha come effetto
	 * la deselezione del ticket selezionato, e l'upload di details, ticketsList,
	 * resList.
	 * 
	 * <p>
	 * È pensato per essere chiamato in seguito alla compilazione di un nuovo
	 * ticket.
	 * </p>
	 */
	private void reload() {
		model.unselectUser();
		Stream.of(details, ticketsList, resList, usersList).forEach(Controller::updateView);
		context.startTask();
	}

	/**
	 * Metodo per eliminare il ticket selezionato.
	 * 
	 * <p>
	 * È pensato per essere chiamato in seguito al click sul bottone di delete
	 * </p>
	 * 
	 * @param ticketId id del ticketd che si intende eliminare
	 */
	private void detailsDelete() {
		if (model.isTicketSelected()) {
			context.deleteTicket(model.getSelectedTicket());
			model.unselectUser();
			Stream.of(details, ticketsList, resList).forEach(Controller::updateView);
			context.startTask();
		} else {
			JOptionPane.showMessageDialog(null, "Selezionare prima un ticket");
		}
	}

	/**
	 * Metodo che contiene le istruzioni da eseguire per ricaricare le view.
	 * 
	 * <p>
	 * È pensato per essere chiamato in seguito all'aggiornamento del context.
	 * </p>
	 */
	private void contextUpdate() {
		Stream.of(resList, ticketsList).forEach(Controller::updateView);

	}

	/**
	 * Metodo che restituisce il component principale del controller, cioè la view
	 * del MVC
	 * 
	 * @return Component
	 */
	@Override
	public Component getView() {
		return this.view.getMainComponent();
	}

	/**
	 * Metodo che imposta la data selezionata del model. Conseguentemente aggiorna
	 * resList che sarà caricata con gli appuntamenti per la data selezioanta
	 * 
	 * @param date data per la quale si vogliono visualizzare gli appuntamenti.
	 */
	private void dateSelect(LocalDate date) {
		System.out.println(date.toString());
		model.setSelectedDate(date);
		// resList.setModelSupplier(() -> {
		// return new ReservationsListModel(
		// context.getReservationsForDate(model.getSelectedDate()).stream().map(ticket
		// -> {
		// UserData user = context.getUser(ticket.user).get();
		// return new ReservationModel(user.firstName + " " + user.lastName,
		// AppUtils.localDateTimeToString(ticket.booking), ticket.user, ticket.id,
		// ticket.state);
		// }).collect(Collectors.toList()));
		// });
		resList.updateView();
	}

	private void allDateSelect(LocalDate date) {
		System.out.println(date.toString());
		model.setSelectedDate(date);

		// esList.setModelSupplier(() -> {
		// return new ReservationsListModel(
		// context.getReservationsStartToDate(LocalDate.now()).stream().map(ticket -> {
		// UserData user = context.getUser(ticket.user).get();
		// return new ReservationModel(user.firstName + " " + user.lastName,
		// AppUtils.localDateTimeToString(ticket.booking), ticket.user, ticket.id,
		// ticket.state);
		// }).collect(Collectors.toList()));
		// );

		resList.updateView();
	}

	/**
	 * Metodo che aggiunge l'utente passato come parametro alla lista di user del
	 * context. Viene presentata una schermata di dialog contestualmente al fatto
	 * che l'utente sia già presente o meno.
	 * 
	 * <p>
	 * È pensato per essere chiamato in seguito al click sul bottone di salvataggio
	 * del form per caricare un nuovo utente.
	 * </p>
	 * 
	 * @param newUser utente che si intende caricare nel context
	 */
	private void patientSave(UserData newUser) {

		Optional<UserData> existing = context.getUser(newUser.id);

		if (existing.isEmpty()) {
			context.setUser(newUser);
			JOptionPane.showMessageDialog(null, "Nuovo utente salvato con successo!");

		} else {
			context.setUser(newUser);
			JOptionPane.showMessageDialog(null, "Le informazioni per l'utente sono state aggiornate");
		}
		usersList.updateView();
	}

	private void patientSearch(UserData searchUser) {

		List<UserData> results = context.searchUsers(searchUser);

		usersList.setModelSupplier(() -> {
			return new UsersListModel(results.stream().map(user -> {
				return new UserModel(user.firstName + " " + user.lastName, user.id);
			}).collect(Collectors.toList()));
		});

		usersList.updateView();
	}

	/**
	 * Metodo per caricare la schermata dei dettagli per il ticket selezionato nella
	 * lista dei ticket in attesa. Provvede ad arrestare il processo di
	 * aggiornamento del context, per scongiurare che esso alteri la dashboard
	 * fintanto che l'utente ha delle operazioni non ancora salvate.
	 * 
	 * <p>
	 * È pensato per essere chiamato in seguito al click su un elemento della lista
	 * dei ticket in attesa.
	 * </p>
	 * 
	 * @param userId
	 */
	private void ticketSelect(Senders sender, String ticketId, String userId) {
		model.setSelectedTicket(ticketId);
		model.setSelectedUser(userId);
		if (sender == Senders.RES_LIST) {
			ticketsList.updateView();
		} else if (sender == Senders.TICKETS_LIST) {
			resList.updateView();
		}

		context.stopTask();
		// model.setSelectedUser(ticketId);

		// resList.updateView();
		usersList.updateView();
		details.updateView();
	}

	private void userSelect(String userId) {

		model.setSelectedUser(userId);
		model.unselectTicket();
		details.updateView();
		ticketsList.updateView();
		resList.updateView();
		// usersList.updateView();
		// context.stopTask();
	}

	/**
	 * Metodo per verificare se sia possibile aggiungere un appuntamento per la data
	 * e l'ora passata come parametro.
	 * 
	 * @param candidateDateTime data ed ora candidate per l'aggiunta di un nuovo
	 *                          appunamento
	 * @return boolean risultato del controllo nella lista di appuntamenti
	 */
	private boolean detailsValidate(LocalDateTime candidateDateTime) {
		return context.isValidReservation(candidateDateTime);
	}

	/**
	 * Metodo per l'aggiunta di un nuovo apputamento, una volta terminata la
	 * processazione di un ticket in attesa esso viene reinserito in stato di attesa
	 * di riscontro da parte del paziente, e deve essere quindi rimosso dalla
	 * TicketList per essere eventualmente visualizzato in ReservationsList.
	 * 
	 * <p>
	 * È pensato per essere chiamato in seguito al click sul bottone per salvare
	 * all'interno della schermata dei dettagli
	 * </p>
	 * 
	 * @param newTicket il ticket da sostituire alla versione non processata nel
	 *                  context.
	 */
	private void detailsSave(TicketData newTicket) {
		context.setTicket(newTicket);

		reload();
	}

	private void sendMessage(String message) {
		context.sendMessage(model.getSelectedUser(), message); // todo mettere l'id dell'utente
		// reload();
	}

	/**
	 * Metodo pensato per rappresentare mediante una finestra di dialogo eventuali
	 * eccezioni sollevate dai problemi di lettura e scrittura provenienti dal
	 * ContextManager.
	 * 
	 * @param e
	 */
	private void dataException(Exception e) {
		JOptionPane.showMessageDialog(null,
				"Verifica che il programma sia in una cartella con i permessi di lettura e scrittura e che ci sia spazio sul disco",
				"Errore di lettura o scrittura su file", JOptionPane.ERROR_MESSAGE);
		System.exit(0);
	}

	/**
	 * Metodo pensato per rappresentare mediante una finestra di dialogo eventuali
	 * eccezioni sollevate dai problemi in fase di invio o ricezione delle email.
	 * 
	 * @param e
	 */
	private void emailException(Exception e) {
		JOptionPane.showMessageDialog(null,
				"Verifica la connessione o la configurazione del tuo account di posta. Prova ad accedere all'account dal browser,\nse il problema persiste assicurati di aver concesso l'esecuzione alle app meno sicure",
				"Errore nel servizio di email", JOptionPane.ERROR_MESSAGE);
		System.exit(0);
	}
}
