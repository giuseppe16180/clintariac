package com.clintariac.components.calendar;

import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.time.LocalDate;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import com.clintariac.components.mvc.View;
import com.clintariac.services.utils.AppUtils;
import com.github.lgooddatepicker.components.CalendarPanel;
import com.github.lgooddatepicker.components.DatePickerSettings;

public class CalendarView implements View {

    private JPanel mainPanel;
    private DatePickerSettings settings;
    private CalendarPanel calendarPanel;
    private JButton allReserv;

    public CalendarView() {

        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(AppUtils.createMainBorder("Seleziona giornata"));

        settings = new DatePickerSettings();
        settings.setFontCalendarDateLabels(AppUtils.text);
        settings.setFontValidDate(AppUtils.text);
        settings.setFontClearLabel(AppUtils.text);
        settings.setFontTodayLabel(AppUtils.text);
        settings.setFontMonthAndYearMenuLabels(AppUtils.text);
        settings.setFontCalendarWeekdayLabels(AppUtils.text);

        calendarPanel = new CalendarPanel(settings);

        calendarPanel.setBorder(new LineBorder(Color.lightGray));

        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;

        mainPanel.add(calendarPanel, gbc);

        allReserv = new JButton();
        allReserv.setHorizontalTextPosition(0);
        allReserv.setText("Tutti gli appuntamenti");
        allReserv.setFont(AppUtils.text);
        allReserv.setMargin(new Insets(3, 3, 3, 3));

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(12, 12, 12, 12);

        mainPanel.add(allReserv, gbc);
    }

    /**
     * @return Component
     */
    @Override
    public Component getMainComponent() {
        return mainPanel;
    }

    /**
     * @return CalendarPanel
     */
    public CalendarPanel getCalendarPanel() {
        return calendarPanel;
    }
}
