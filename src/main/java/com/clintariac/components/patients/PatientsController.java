package com.clintariac.components.patients;

import java.awt.Component;
import java.util.function.Consumer;
import javax.swing.JOptionPane;
import com.clintariac.components.mvc.Controller;
import com.clintariac.data.UserData;

public class PatientsController implements Controller {

    private PatientsView view;

    private Consumer<UserData> onSave;

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
                    view.getUserIdField().getText().toUpperCase(),
                    view.getEmailField().getText(),
                    view.getPhoneField().getText());

            onSave.accept(newUser);
            resetView();

        } else {

            JOptionPane.showMessageDialog(null, message.toString(),
                    "Informazioni utente non valide",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void resetView() {
        view.getFirstNameField().setText("");
        view.getLastNameField().setText("");
        view.getUserIdField().setText("");
        view.getEmailField().setText("");
        view.getPhoneField().setText("");
    }
}
