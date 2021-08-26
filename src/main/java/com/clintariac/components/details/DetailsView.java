package com.clintariac.components.details;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import com.clintariac.components.details.chat.ChatController;
import com.clintariac.components.mvc.View;
import com.clintariac.services.config.Preferences;
import com.clintariac.services.utils.AppUtils;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;

public class DetailsView implements View {

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextField userIdField;
    private JTextField ticketIdField;
    private JTextPane messagePane;
    private JButton validateButton;
    private JButton saveButton;
    private JButton deleteButton;
    private DateTimePicker dateTimePicker;
    private JButton sendButton;
    private DatePickerSettings dateSettings;
    private TimePickerSettings timeSettings;

    private JPanel mainPanel;

    private ChatController chatController;

    public DetailsView() {

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        mainPanel.setBorder(AppUtils.createMainBorder("Gestione ticket"));

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
        firstNameField.setEditable(false);
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
        lastNameField.setEditable(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.ipadx = 100;
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
        phoneField.setEditable(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.ipadx = 100;
        gbc.anchor = GridBagConstraints.WEST;
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
        emailField.setEditable(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.ipadx = 100;
        gbc.anchor = GridBagConstraints.WEST;
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
        userIdField.setEditable(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.ipadx = 100;
        panel6.add(userIdField, gbc);


        final JPanel panel10 = new JPanel();
        panel10.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.VERTICAL;
        mainPanel.add(panel10, gbc);

        final JLabel ticketIdLabel = new JLabel();
        ticketIdLabel.setText("Ticket: ");
        ticketIdLabel.setFont(AppUtils.text);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel10.add(ticketIdLabel, gbc);

        ticketIdField = new JTextField();
        ticketIdField.setColumns(15);
        ticketIdField.setFont(AppUtils.text);
        ticketIdField.setEditable(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.ipadx = 100;
        panel10.add(ticketIdField, gbc);



        final JPanel spacer4 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.ipady = 20;
        mainPanel.add(spacer4, gbc);

        chatController = new ChatController();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 5;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(3, 8, 3, 8);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.ipady = 260;
        mainPanel.add(chatController.getView(), gbc);



        final JPanel spacer7 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.ipady = 0;
        mainPanel.add(spacer7, gbc);



        messagePane = new JTextPane();
        messagePane.setFont(AppUtils.text);
        messagePane.setEditable(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 5;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(3, 8, 3, 8);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.ipady = 30;

        final JScrollPane scollPane = new JScrollPane(messagePane);
        scollPane.setBorder(AppUtils.createMainBorder("Rispondi"));
        mainPanel.add(scollPane, gbc);



        sendButton = new JButton();
        sendButton.setFont(AppUtils.text);
        sendButton.setHorizontalTextPosition(0);
        sendButton.setText("Invia");
        sendButton.setMargin(new Insets(3, 3, 3, 3));
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 10;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(3, 8, 0, 8);
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(sendButton, gbc);


        final JPanel spacer5 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.ipady = 30;
        mainPanel.add(spacer5, gbc);



        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridBagLayout());
        panel7.setBorder(AppUtils.createSimpleBorderTop("Gestione prenotazione"));
        gbc.gridx = 0;
        gbc = new GridBagConstraints();
        gbc.gridy = 12;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridwidth = 5;
        gbc.insets = new Insets(3, 8, 3, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(panel7, gbc);

        dateSettings = new DatePickerSettings();
        timeSettings = new TimePickerSettings();

        dateTimePicker = new DateTimePicker(dateSettings, timeSettings);

        dateSettings.setDateRangeLimits(LocalDate.now(), null);
        dateSettings.setAllowKeyboardEditing(false);

        dateSettings.setFontCalendarDateLabels(AppUtils.text);
        dateSettings.setFontValidDate(AppUtils.text);
        dateSettings.setFontClearLabel(AppUtils.text);
        dateSettings.setFontTodayLabel(AppUtils.text);

        dateSettings.setFontMonthAndYearMenuLabels(AppUtils.text);
        dateSettings.setFontCalendarWeekdayLabels(AppUtils.text);

        timeSettings.generatePotentialMenuTimes(Preferences.examDuration, Preferences.openingTime,
                Preferences.closingTime.minusMinutes(Preferences.examDuration.minutes));
        timeSettings.setAllowKeyboardEditing(false);
        timeSettings.fontValidTime = AppUtils.text;
        timeSettings.fontInvalidTime = AppUtils.text;
        timeSettings.fontVetoedTime = AppUtils.text;
        timeSettings.setSizeTextFieldMinimumWidth(40);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(12, 36, 3, 36);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        panel7.add(dateTimePicker, gbc);

        final JPanel bottomPanel = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel7.add(bottomPanel, gbc);

        deleteButton = new JButton();
        deleteButton.setHorizontalTextPosition(0);
        deleteButton.setText("Elimina");
        deleteButton.setFont(AppUtils.text);
        deleteButton.setMargin(new Insets(3, 3, 3, 3));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        bottomPanel.add(deleteButton, gbc);

        validateButton = new JButton();
        validateButton.setHorizontalTextPosition(0);
        validateButton.setText("Controlla");
        validateButton.setFont(AppUtils.text);
        validateButton.setMargin(new Insets(3, 3, 3, 3));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        bottomPanel.add(validateButton, gbc);

        saveButton = new JButton();
        saveButton.setFont(AppUtils.text);
        saveButton.setHorizontalTextPosition(0);
        saveButton.setText("Salva");
        saveButton.setMargin(new Insets(3, 3, 3, 3));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        bottomPanel.add(saveButton, gbc);
    }


    /**
     * @return Component
     */
    @Override
    public Component getMainComponent() {
        return mainPanel;
    }


    /**
     * @return JTextField
     */
    public JTextField getFirstNameField() {
        return firstNameField;
    }


    /**
     * @return JTextField
     */
    public JTextField getLastNameField() {
        return lastNameField;
    }


    /**
     * @return JTextField
     */
    public JTextField getPhoneField() {
        return phoneField;
    }


    /**
     * @return JTextField
     */
    public JTextField getEmailField() {
        return emailField;
    }

    /**
     * @return JTextField
     */
    public JTextField getUserField() {
        return userIdField;
    }


    /**
     * @return JTextField
     */
    public JTextField getTicketField() {
        return ticketIdField;
    }

    /**
     * @return JTextPane
     */
    public JTextPane getMessagePane() {
        return messagePane;
    }


    /**
     * @return JButton
     */
    public JButton getSaveButton() {
        return saveButton;
    }


    /**
     * @return JButton
     */
    public JButton getValidateButton() {
        return validateButton;
    }


    /**
     * @return JButton
     */
    public JButton getDeleteButton() {
        return deleteButton;
    }

    /**
     * @return JButton
     */
    public JButton getSendButton() {
        return sendButton;
    }

    /**
     * @return DateTimePicker
     */
    public DateTimePicker getDateTimePicker() {
        return dateTimePicker;
    }


    /**
     * @return DatePickerSettings
     */
    public DatePickerSettings getDateSettings() {
        return dateSettings;
    }


    /**
     * @param dateSettings
     */
    public void setDateSettings(DatePickerSettings dateSettings) {
        this.dateSettings = dateSettings;
    }


    /**
     * @return TimePickerSettings
     */
    public TimePickerSettings getTimeSettings() {
        return timeSettings;
    }


    /**
     * @param timeSettings
     */
    public void setTimeSettings(TimePickerSettings timeSettings) {
        this.timeSettings = timeSettings;
    }


    /**
     * @return JTextField
     */
    public JTextField getTicketIdField() {
        return ticketIdField;
    }


    /**
     * @return ChatController
     */
    public ChatController getChatController() {
        return chatController;
    }
}
