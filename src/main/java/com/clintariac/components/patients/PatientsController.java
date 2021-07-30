package com.clintariac.components.patients;

import java.awt.Component;
import java.util.function.Consumer;
import javax.swing.JOptionPane;
import com.clintariac.components.mvc.Controller;
import com.clintariac.data.UserData;

public class PatientsController implements Controller {

    private PatientsView view;

    private Consumer<UserData> onSave;
    private Consumer<UserData> onEdit;
    private Consumer<UserData> onSearch;
    private Consumer<UserData> onClear;

    public PatientsController() {

        this.view = new PatientsView();
        init();
    }

    /**
     * @return Component
     */
    @Override
    public Component getView() {
        return this.view.getMainComponent();
    }

    private void init() {

        view.getSaveButton().addActionListener(e -> save());
        view.getEditButton().addActionListener(e -> edit());
        view.getSearchButton().addActionListener(e -> search());
        view.getClearButton().addActionListener(e -> clear());
    }

    /**
     * @param onSave
     */
    public void addOnSave(Consumer<UserData> onSave) {
        this.onSave = onSave;
    }

    private void save() {

        boolean isCorrect = true;

        StringBuilder message = new StringBuilder();

        if (view.getFirstNameField().getText().isEmpty()
                || view.getLastNameField().getText().isEmpty()) {

            message.append("Inserire tutti i campi!\n");
            isCorrect = false;
        }

        // controllo sul numero di telefono
        if (!view.getPhoneField().getText().matches("[0-9]{8,10}")) {

            message.append("È presente un errore nel numero di telefono!\n");
            isCorrect = false;
        }

        // controllo email
        if (!view.getEmailField().getText()
                .matches("^[A-Za-z0-9+_.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)")) {

            message.append("È presente un errore nella mail!\n");
            isCorrect = false;
        }

        // controllo codice fiscale
        if (!view.getUserIdField().getText().toUpperCase()
                .matches("[A-Z]{6}\\d{2}[A-Z]\\d{2}[A-Z]\\d{3}[A-Z]")) {

            message.append("È presente un errore nel codice fiscale!\n");

            isCorrect = false;
        }

        if (isCorrect) {

            UserData newUser = new UserData(view.getFirstNameField().getText(),
                    view.getLastNameField().getText(),
                    view.getUserIdField().getText().toUpperCase(), view.getEmailField().getText(),
                    view.getPhoneField().getText());

            onSave.accept(newUser);

        } else {

            JOptionPane.showMessageDialog(null, message.toString(),
                    "Informazioni utente non valide",
                    JOptionPane.ERROR_MESSAGE);
        }
    }



    public void addOnEdit(Consumer<UserData> onEdit) {
        this.onEdit = onEdit;
    }

    private void edit() {
        boolean isCorrect = true;

        StringBuilder message = new StringBuilder();

        // controllo sul numero di telefono
        String phone = view.getPhoneField().getText();
        if (!phone.isEmpty() && !view.getPhoneField().getText().matches("[0-9]{8,10}")) {
            message.append("È presente un errore nel numero di telefono!\n");
            isCorrect = false;
        }

        // controllo email
        String email = view.getEmailField().getText();
        if (!email.isEmpty()
                && !email.matches("^[A-Za-z0-9+_.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)")) {
            message.append("È presente un errore nella mail!\n");
            isCorrect = false;
        }

        // controllo codice fiscale
        String id = view.getUserIdField().getText().toUpperCase();
        if (id.isEmpty()) {
            message.append(
                    "È necessario inserire il codice fiscale per aggiornare le informazioni!\n");
            isCorrect = false;
        } else if (!id.matches("[A-Z]{6}\\d{2}[A-Z]\\d{2}[A-Z]\\d{3}[A-Z]")) {
            message.append("È presente un errore nel codice fiscale!\n");
            isCorrect = false;
        }

        if (isCorrect) {

            UserData newUser = new UserData(
                    view.getFirstNameField().getText(),
                    view.getLastNameField().getText(),
                    id, email, phone);

            onEdit.accept(newUser);

        } else {
            JOptionPane.showMessageDialog(null, message.toString(),
                    "Informazioni utente non valide",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * @param onSearch
     */
    public void addOnSearch(Consumer<UserData> onSearch) {
        this.onSearch = onSearch;
    }

    private void search() {
        UserData searchUser = new UserData(view.getFirstNameField().getText().trim(),
                view.getLastNameField().getText().trim(),
                view.getUserIdField().getText().toUpperCase().trim(),
                view.getEmailField().getText().trim(),
                view.getPhoneField().getText().trim());
        onSearch.accept(searchUser);
    }

    public void addOnClear(Consumer<UserData> onClear) {
        this.onClear = onClear;
    }

    private void clear() {
        UserData emptyUser = new UserData();
        reloadView();
        onClear.accept(emptyUser);
    }

    @Override
    public void reloadView() {
        view.getFirstNameField().setText("");
        view.getLastNameField().setText("");
        view.getUserIdField().setText("");
        view.getEmailField().setText("");
        view.getPhoneField().setText("");
    }
}
