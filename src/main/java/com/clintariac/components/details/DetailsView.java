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
    private JTextPane messagePane;
    private JTextField dateSpinner;
    private JTextField timeSpinner;
    private JButton validateButton;
    private JButton saveButton;
    private JButton deleteButton;
    private DateTimePicker dateTimePicker;

    private DatePickerSettings dateSettings;
    private TimePickerSettings timeSettings;

    private JPanel mainPanel;

    public DetailsView() {

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        mainPanel.setBorder(AppUtils.createMainBorder("Gestione tickets"));

        // row 0
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(16, 0, 0, 0);
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.VERTICAL;

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
        firstNameField.setFont(AppUtils.text);
        firstNameField.setColumns(13);
        firstNameField.setEditable(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.ipadx = 150;
        // gbc.fill = GridBagConstraints.HORIZONTAL;
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

        final JLabel label2 = new JLabel();
        label2.setText("Cognome: ");
        label2.setFont(AppUtils.text);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(label2, gbc);

        lastNameField = new JTextField();
        lastNameField.setColumns(13);
        lastNameField.setEditable(false);
        lastNameField.setFont(AppUtils.text);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.ipadx = 150;
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

        final JLabel label4 = new JLabel();
        label4.setText("Telefono: ");
        label4.setFont(AppUtils.text);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel4.add(label4, gbc);

        phoneField = new JTextField();
        phoneField.setFont(AppUtils.text);
        phoneField.setColumns(13);
        phoneField.setEditable(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.ipadx = 150;
        // gbc.fill = GridBagConstraints.HORIZONTAL;
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

        final JLabel label5 = new JLabel();
        label5.setFont(AppUtils.text);
        label5.setText("Email: ");
        label5.setFont(AppUtils.text);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel5.add(label5, gbc);

        emailField = new JTextField();
        emailField.setFont(AppUtils.text);
        emailField.setColumns(13);
        emailField.setEditable(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.ipadx = 150;
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

        final JLabel label6 = new JLabel();
        label6.setFont(AppUtils.text);
        label6.setText("Cod. fisc.: ");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel6.add(label6, gbc);

        userIdField = new JTextField();
        userIdField.setFont(AppUtils.text);
        userIdField.setColumns(13);
        userIdField.setEditable(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        // gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 150;
        panel6.add(userIdField, gbc);


        final JPanel spacer4 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.ipady = 20;
        mainPanel.add(spacer4, gbc);

        final JLabel label8 = new JLabel();
        label8.setFont(AppUtils.text);
        label8.setText("Messaggio:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(3, 8, 3, 3);
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(label8, gbc);

        final JPanel spacer7 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.ipady = 0;
        mainPanel.add(spacer7, gbc);

        messagePane = new JTextPane();
        messagePane.setFont(AppUtils.text);
        messagePane.setEditable(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 5;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(3, 8, 3, 8);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.ipady = 120;
        mainPanel.add(new JScrollPane(messagePane), gbc);

        final JPanel spacer5 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.ipady = 30;
        mainPanel.add(spacer5, gbc);

        final JLabel label7 = new JLabel();
        label7.setFont(AppUtils.text);
        label7.setText("Gestione data e ora per la prenotazione:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 5;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(3, 8, 0, 0);
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(label7, gbc);

        final JPanel spacer6 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.ipady = 8;
        mainPanel.add(spacer6, gbc);

        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
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
        gbc.gridy = 1;
        gbc.ipadx = 10;
        gbc.insets = new Insets(3, 8, 3, 3);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        panel7.add(dateTimePicker, gbc);

        final JPanel spacer8 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 13;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.ipady = 26;
        mainPanel.add(spacer8, gbc);

        deleteButton = new JButton();
        deleteButton.setFont(AppUtils.text);
        deleteButton.setHorizontalTextPosition(0);
        deleteButton.setText("Elimina");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 13;
        deleteButton.setMargin(new Insets(3, 3, 3, 3));
        mainPanel.add(deleteButton, gbc);

        final JPanel spacer9 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 13;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 100;
        mainPanel.add(spacer9, gbc);

        validateButton = new JButton();

        validateButton.setHorizontalTextPosition(0);
        validateButton.setText("Controlla");
        validateButton.setFont(AppUtils.text);
        validateButton.setMargin(new Insets(3, 3, 3, 3));
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 13;
        mainPanel.add(validateButton, gbc);

        saveButton = new JButton();
        saveButton.setFont(AppUtils.text);
        saveButton.setHorizontalTextPosition(0);
        saveButton.setText("Salva");
        saveButton.setMargin(new Insets(3, 3, 3, 3));
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 13;
        mainPanel.add(saveButton, gbc);

        final JPanel spacer10 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 13;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 100;
        mainPanel.add(spacer10, gbc);
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
     * @return JTextPane
     */
    public JTextPane getMessagePane() {
        return messagePane;
    }


    /**
     * @return JTextField
     */
    public JTextField getDateSpinner() {
        return dateSpinner;
    }


    /**
     * @return JTextField
     */
    public JTextField getTimeSpinner() {
        return timeSpinner;
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
}
