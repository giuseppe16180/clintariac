package com.clintariac.components.details;

import java.awt.Component;
import java.time.LocalDateTime;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javax.swing.JOptionPane;
import com.clintariac.components.mvc.Controller;
import com.clintariac.data.TicketData;
import com.clintariac.data.TicketState;
import com.clintariac.services.config.Preferences;
import com.clintariac.services.utils.Procedure;

/**
 * DetailsController
 * 
 * La classe controller è predisposta per gestire la visualizzazione dei dettagli di un ticket e
 * permette di effettuare operazioni di modifica, salvataggio o eliminazione.
 */

public class DetailsController implements Controller {

    private DetailsModel model;
    private DetailsView view;

    private Supplier<DetailsModel> modelSupplier;

    private Predicate<LocalDateTime> onValidate;
    private Consumer<TicketData> onSave;
    private Procedure onDelete;
    private Consumer<String> onSend;

    /**
     * Il costruttore di DetailsController instanzia la view e inizializza gli ascoltatori
     * attraverso il metodo {@code init()}.
     */
    public DetailsController() {

        this.view = new DetailsView();
        init();
    }


    /**
     * Metodo per istanziare il modello di Details attraverso un {@code Supplier<DetailsModel>}
     * ricevuto a parametro.
     * 
     * @param modelSupplier modello
     * @return DetailsController
     */
    public DetailsController setModelSupplier(Supplier<DetailsModel> modelSupplier) {
        this.modelSupplier = modelSupplier;
        return this;
    }

    /**
     * Metodo per caricare nella view le informazioni di un ticket contenute nel model.
     */
    public void updateView() {

        model = modelSupplier.get();
        view.getFirstNameField().setText(model.getFirstName());
        view.getLastNameField().setText(model.getLastName());
        view.getEmailField().setText(model.getEmail());
        view.getPhoneField().setText(model.getPhone());
        view.getUserField().setText(model.getUserId());
        view.getMessagePane().setText(model.getMessage());
        view.getValidateButton().setEnabled(model.isAwaiting());
        view.getSaveButton().setEnabled(false);
        view.getDateTimePicker().setDateTimeStrict(model.getDateTime());
        view.getDateTimePicker().setEnabled(model.isAwaiting());
    }


    /**
     * @return Component
     */
    @Override
    public Component getView() {
        return this.view.getMainComponent();
    }



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
     * @param onSave
     */
    public void addOnSave(Consumer<TicketData> onSave) {
        this.onSave = onSave;
    }


    /**
     * @param onValidate
     */
    public void addOnValidate(Predicate<LocalDateTime> onValidate) {
        this.onValidate = onValidate;
    }


    /**
     * @param onDelete
     */
    public void addOnDelete(Procedure onDelete) {
        this.onDelete = onDelete;
    }

    /**
     * @param onSend
     */
    public void addOnSend(Consumer<String> onSend) {
        this.onSend = onSend;
    }

    private void save() {

        LocalDateTime dateTime = view.getDateTimePicker().getDateTimeStrict();
        TicketData newTicket =
                new TicketData(
                        model.getTicketId(),
                        model.getUserId(),
                        TicketState.BOOKED,
                        dateTime,
                        LocalDateTime.now(),
                        model.getMessage());
        onSave.accept(newTicket);
        JOptionPane.showMessageDialog(null, "Salvataggio effettuato con successo");
    }

    private void validate() {

        // controllo se il ticket è selezionato
        String ticketId = model.getTicketId();

        if (ticketId.equals("")) {

            JOptionPane.showMessageDialog(
                    null,
                    "Selezionare il ticket che si vuole processare!",
                    "Validazione ticket",
                    JOptionPane.ERROR_MESSAGE);

            return;
        }

        // controllo sulla data e ora
        LocalDateTime dateTime = view.getDateTimePicker().getDateTimeStrict();

        if (dateTime == null) {

            JOptionPane.showMessageDialog(
                    null,
                    "Si prega di impostare sia la data che l'ora dell'appuntamento!",
                    "Appuntamento non valido",
                    JOptionPane.ERROR_MESSAGE);
        }

        else if (dateTime
                .isBefore(LocalDateTime.now().plusMinutes(Preferences.examDuration.minutes))) {

            JOptionPane.showMessageDialog(
                    null,
                    "Si prega di impostare un orario successivo, quello selezionato è trascorso oppure è troppo imminente!",
                    "Appuntamento non valido",
                    JOptionPane.ERROR_MESSAGE);
        }

        else if (onValidate.test(dateTime)) {

            JOptionPane.showMessageDialog(
                    null,
                    "Nessun impegno per la data e l'ora impostati!");

            view.getSaveButton().setEnabled(true);
        } else {

            JOptionPane.showMessageDialog(
                    null,
                    "È già presente un impegno per la data e l'ora impostati!",
                    "Appuntamento non valido",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void delete() {

        String ticketId = model.getTicketId();

        if (!ticketId.isEmpty()) { // ticket selezionato

            int option = JOptionPane.showConfirmDialog(
                    null,
                    "Sei sicuro/a di voler eliminare il ticket selezionato?",
                    "Eliminazione ticket", JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.YES_OPTION) {
                onDelete.run();
                JOptionPane.showMessageDialog(
                        null,
                        "Il ticket è stato eliminato.",
                        "Eliminazione completata!",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } else {

            JOptionPane.showMessageDialog(
                    null,
                    "Selezionare il ticket che si vuole eliminare",
                    "Eliminazione ticket",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void send() {
        onSend.accept(model.getMessage());
        view.getMessagePane().setText("");
    }

    private void didChange() {
        view.getSaveButton().setEnabled(false);
    }

    private void didEditMessage() {
        // System.out.println(view.getMessagePane().getText());
        model.setMessage(view.getMessagePane().getText());
    }
}
