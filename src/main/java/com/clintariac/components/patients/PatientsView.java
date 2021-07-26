package com.clintariac.components.patients;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.clintariac.components.mvc.View;
import com.clintariac.services.utils.AppUtils;

public class PatientsView implements View {

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextField userIdField;
    private JButton saveButton;
    private JButton searchButton;
    private JButton clearButton;

    private JPanel mainPanel;

    public PatientsView() {

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        mainPanel.setBorder(AppUtils.createMainBorder("Gestione pazienti"));

        // row 0
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(16, 0, 0, 0);
        mainPanel.add(panel2, gbc);

        final JLabel firstNameLabel = new JLabel();
        firstNameLabel.setText("Nome: ");
        firstNameLabel.setFont(AppUtils.text);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel2.add(firstNameLabel, gbc);

        firstNameField = new JTextField();
        firstNameField.setColumns(15);
        firstNameField.setFont(AppUtils.text);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.ipadx = 100;
        panel2.add(firstNameField, gbc);

        // row 1
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.VERTICAL;
        mainPanel.add(panel3, gbc);

        final JLabel lastNameLabel = new JLabel();
        lastNameLabel.setText("Cognome: ");
        lastNameLabel.setFont(AppUtils.text);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(lastNameLabel, gbc);

        lastNameField = new JTextField();
        lastNameField.setColumns(15);
        lastNameField.setFont(AppUtils.text);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.ipadx = 100;
        // gbc.fill = GridBagConstraints.HORIZONTAL;
        panel3.add(lastNameField, gbc);

        // row 2
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.VERTICAL;
        mainPanel.add(panel4, gbc);

        final JLabel phoneLabel = new JLabel();
        phoneLabel.setText("Telefono: ");
        phoneLabel.setFont(AppUtils.text);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel4.add(phoneLabel, gbc);

        phoneField = new JTextField();
        phoneField.setColumns(15);
        phoneField.setFont(AppUtils.text);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.ipadx = 100;
        gbc.anchor = GridBagConstraints.WEST;
        // gbc.fill = GridBagConstraints.HORIZONTAL;
        // gbc.ipadx = 22;
        panel4.add(phoneField, gbc);

        // row 3
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.VERTICAL;
        mainPanel.add(panel5, gbc);

        final JLabel emailLabel = new JLabel();
        emailLabel.setText("Email: ");
        emailLabel.setFont(AppUtils.text);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel5.add(emailLabel, gbc);

        emailField = new JTextField();
        emailField.setColumns(15);
        emailField.setFont(AppUtils.text);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.ipadx = 100;
        gbc.anchor = GridBagConstraints.WEST;
        // gbc.fill = GridBagConstraints.HORIZONTAL;
        panel5.add(emailField, gbc);

        // row 4
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.VERTICAL;
        mainPanel.add(panel6, gbc);

        final JLabel userIdLabel = new JLabel();
        userIdLabel.setText("Cod. fisc.: ");
        userIdLabel.setFont(AppUtils.text);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel6.add(userIdLabel, gbc);

        userIdField = new JTextField();
        userIdField.setColumns(15);
        userIdField.setFont(AppUtils.text);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.ipadx = 100;
        panel6.add(userIdField, gbc);

        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 22;
        gbc.ipady = 16;
        mainPanel.add(spacer1, gbc);

        saveButton = new JButton();
        saveButton.setHorizontalTextPosition(0);
        saveButton.setFont(AppUtils.text);
        saveButton.setText("Aggiungi");
        saveButton.setMargin(new Insets(3, 3, 3, 3));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 12, 12, 12);
        mainPanel.add(saveButton, gbc);

        searchButton = new JButton();
        searchButton.setHorizontalTextPosition(0);
        searchButton.setFont(AppUtils.text);
        searchButton.setText("Ricerca");
        searchButton.setMargin(new Insets(3, 3, 3, 3));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 6;
        mainPanel.add(searchButton, gbc);

        clearButton = new JButton();
        clearButton.setHorizontalTextPosition(0);
        clearButton.setFont(AppUtils.text);
        clearButton.setText("Pulisci");
        clearButton.setMargin(new Insets(3, 3, 3, 3));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 7;
        mainPanel.add(clearButton, gbc);

    }

    /**
     * @return JTextField
     */
    public JTextField getFirstNameField() {
        return firstNameField;
    }

    /**
     * @param firstNameField
     */
    public void setFirstNameField(JTextField firstNameField) {
        this.firstNameField = firstNameField;
    }

    /**
     * @return JTextField
     */
    public JTextField getLastNameField() {
        return lastNameField;
    }

    /**
     * @param lastNameField
     */
    public void setLastNameField(JTextField lastNameField) {
        this.lastNameField = lastNameField;
    }

    /**
     * @return JTextField
     */
    public JTextField getPhoneField() {
        return phoneField;
    }

    /**
     * @param phoneField
     */
    public void setPhoneField(JTextField phoneField) {
        this.phoneField = phoneField;
    }

    /**
     * @return JTextField
     */
    public JTextField getEmailField() {
        return emailField;
    }

    /**
     * @param emailField
     */
    public void setEmailField(JTextField emailField) {
        this.emailField = emailField;
    }

    /**
     * @return JTextField
     */
    public JTextField getUserIdField() {
        return userIdField;
    }

    /**
     * @param userIdField
     */
    public void setUserIdField(JTextField userIdField) {
        this.userIdField = userIdField;
    }

    /**
     * @return JButton
     */
    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JButton getClearButton() {
        return clearButton;
    }

    /**
     * @return JPanel
     */
    @Override
    public JPanel getMainComponent() {
        return mainPanel;
    }
}
