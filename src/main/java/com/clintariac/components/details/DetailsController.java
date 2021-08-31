package com.clintariac.components.details;

import java.awt.Component;
import java.time.LocalDateTime;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import com.clintariac.components.details.chat.ChatController;
import com.clintariac.components.details.chat.ChatModel;
import com.clintariac.components.details.chat.message.MessageModel;
import com.clintariac.components.mvc.Controller;
import com.clintariac.data.TicketData;
import com.clintariac.data.TicketState;
import com.clintariac.services.config.Preferences;
import com.clintariac.services.utils.AppUtils;
import com.clintariac.services.utils.Procedure;

/**
 * 
 * La classe DetailsController è predisposta per gestire la visualizzazione dei dettagli di un
 * ticket, la conversazione con il paziente e permette di effettuare operazioni di modifica,
 * salvataggio o eliminazione sui ticket.
 */

public class DetailsController implements Controller {

    private DetailsModel model;
    private DetailsView view;

    private Supplier<DetailsModel> modelSupplier;

    private Predicate<LocalDateTime> onValidate;
    private Consumer<TicketData> onSave;
    private Procedure onDelete;
    private Consumer<String> onSend;
    private Consumer<LocalDateTime> onCreateNewTicket;

    private ChatController chat;

    /**
     * Il costruttore di DetailsController instanzia la view e inizializza gli ascoltatori
     * attraverso il metodo {@code init()}. Definisce il supplier di default per la chat.
     */
    public DetailsController() {

        this.view = new DetailsView();

        chat = view.getChatController();
        chat.setModelSupplier(() -> {
            return new ChatModel(model.getChat().stream()
                    .map(it -> new MessageModel(
                            AppUtils.plainTextToHTML(it.text, 4),
                            AppUtils.localDateTimeToString(it.dateTime),
                            it.isUserSent))
                    .collect(Collectors.toList()));
        });

        init();
    }

    /**
     * Metodo per definire il supplier del modello di Details dall'esterno, attraverso un
     * {@code Supplier<DetailsModel>} ricevuto a parametro.
     * 
     * @param modelSupplier modello
     * @return DetailsController
     */
    public DetailsController setModelSupplier(Supplier<DetailsModel> modelSupplier) {
        this.modelSupplier = modelSupplier;
        return this;
    }

    /**
     * Metodo per caricare le view con le informazioni nel model ottenuto dal get sul supplier.
     * Provoca l'azzeramento dello stato delle view, ripristinando gli aspetti dovuti
     * all'interazione dell'utente con la UI.
     */
    @Override
    public void reloadView() {
        model = modelSupplier.get();
        view.getFirstNameField().setText(model.getFirstName());
        view.getLastNameField().setText(model.getLastName());
        view.getEmailField().setText(model.getEmail());
        view.getPhoneField().setText(model.getPhone());
        view.getUserField().setText(model.getUserId());
        view.getTicketField().setText(model.getTicketId());
        view.getMessagePane().setText(model.getMessage());
        view.getDeleteButton().setEnabled(
                !model.getTicketId().isEmpty() && model.getTicketState() != TicketState.DELETED);
        view.getValidateButton().setEnabled(!model.getUserId().isEmpty());
        view.getSaveButton().setEnabled(false);
        view.getDateTimePicker().setDateTimeStrict(model.getDateTime());
        view.getDateTimePicker().setEnabled(!model.getUserId().isEmpty());
        chat.reloadView();
    }

    /**
     * Metodo per aggiornare le view con le informazioni nel model ottenuto dal get sul supplier.
     * Provoca l'aggiornamento delle view senza ripristinare gli aspetti dovuti all'interazione
     * dell'utente con la UI.
     */
    @Override
    public void updateView() {
        String message = model.getMessage();
        model = modelSupplier.get();
        model.setMessage(message);
        view.getDeleteButton().setEnabled(model.getTicketState() != TicketState.DELETED);
        chat.updateView();
    }

    /**
     * @return Component
     */
    @Override
    public Component getView() {
        return this.view.getMainComponent();
    }


    /**
     * Metodo che inizializza gli ascoltatori necessari per l'interazione con l'utente e la gestione
     * dei ticket.
     * 
     */
    private void init() {

        view.getValidateButton().addActionListener(e -> validate());
        view.getSaveButton().addActionListener(e -> save());
        view.getDeleteButton().addActionListener(e -> delete());
        view.getDateTimePicker().addDateTimeChangeListener(e -> didChange());
        view.getSendButton().addActionListener(e -> send());
        view.getMessagePane().getDocument()
                .addDocumentListener((DocumentUpdateListener) (e) -> didEditMessage());
    }

    /**
     * Metodo per definire dall'esterno la funzione da chiamare in seguito alla procedura di save
     * 
     * @param onSave
     */
    public void addOnSave(Consumer<TicketData> onSave) {
        this.onSave = onSave;
    }

    /**
     * Metodo per definire dall'esterno la funzione da chiamare in seguito alla procedura di
     * validate
     * 
     * @param onValidate
     */
    public void addOnValidate(Predicate<LocalDateTime> onValidate) {
        this.onValidate = onValidate;
    }

    /**
     * Metodo per definire dall'esterno la funzione da chiamare in seguito alla procedura di delete
     * 
     * @param onDelete
     */
    public void addOnDelete(Procedure onDelete) {
        this.onDelete = onDelete;
    }

    /**
     * Metodo per definire dall'esterno la funzione da chiamare in seguito alla procedura di send
     * 
     * @param onSend
     */
    public void addOnSend(Consumer<String> onSend) {
        this.onSend = onSend;
    }

    /**
     * Metodo per definire dall'esterno la funzione da chiamare in seguito alla procedura di
     * creazione nuovo ticket
     * 
     * @param onCreateNewTicket
     */
    public void addOnCreateNewTicket(Consumer<LocalDateTime> onCreateNewTicket) {
        this.onCreateNewTicket = onCreateNewTicket;
    }

    /** */


    private void save() {

        LocalDateTime dateTime = view.getDateTimePicker().getDateTimeStrict();

        if (!model.getTicketId().isEmpty()) {
            TicketData newTicket = new TicketData(
                    model.getTicketId(),
                    model.getUserId(),
                    TicketState.BOOKED,
                    dateTime,
                    LocalDateTime.now(),
                    model.getMessage());

            onSave.accept(newTicket);
            JOptionPane.showMessageDialog(null, "Salvataggio effettuato con successo");
        } else {
            onCreateNewTicket.accept(dateTime);
            JOptionPane.showMessageDialog(null, "Prenotazione effettuata con successo");
        }
    }


    private void validate() {

        LocalDateTime dateTime = view.getDateTimePicker().getDateTimeStrict();

        if (dateTime == null) {
            JOptionPane.showMessageDialog(null,
                    "Si prega di impostare sia la data che l'ora dell'appuntamento!",
                    "Appuntamento non valido", JOptionPane.ERROR_MESSAGE);
        }

        else if (dateTime.isBefore(LocalDateTime.now()
                .plusMinutes(Preferences.examDuration.minutes))) {

            JOptionPane.showMessageDialog(null,
                    "Si prega di impostare un orario successivo, quello selezionato è trascorso oppure è troppo imminente!",
                    "Appuntamento non valido", JOptionPane.ERROR_MESSAGE);
        }

        else if (onValidate.test(dateTime)) {
            view.getSaveButton().setEnabled(true);
            JOptionPane.showMessageDialog(null, "Nessun impegno per la data e l'ora impostati!");

        } else {
            JOptionPane.showMessageDialog(null,
                    "È già presente un impegno per la data e l'ora impostati!",
                    "Appuntamento non valido", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void delete() {

        String ticketId = model.getTicketId();

        if (!ticketId.isEmpty()) {

            int option = JOptionPane.showConfirmDialog(null,
                    "Sei sicuro/a di voler eliminare il ticket selezionato?",
                    "Eliminazione ticket", JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.YES_OPTION) {
                onDelete.run();
                JOptionPane.showMessageDialog(null, "Il ticket è stato eliminato.",
                        "Eliminazione completata!", JOptionPane.INFORMATION_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Selezionare il ticket che si vuole eliminare",
                    "Eliminazione ticket", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void send() {
        onSend.accept(model.getMessage());
        view.getMessagePane().setText("");
        reloadView();
    }

    private void didChange() {
        view.getSaveButton().setEnabled(false);
    }

    private void didEditMessage() {
        model.setMessage(view.getMessagePane().getText());
    }
}
