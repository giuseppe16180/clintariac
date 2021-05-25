package com.clintariac.components.calendar;

import java.awt.Color;
import java.awt.Component;
import java.time.LocalDate;
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

    public CalendarView() {

        mainPanel = new JPanel();
        mainPanel.setBorder(AppUtils.createMainBorder("Seleziona giornata"));

        settings = new DatePickerSettings();
        settings.setFontCalendarDateLabels(AppUtils.text);
        settings.setFontValidDate(AppUtils.text);
        settings.setFontClearLabel(AppUtils.text);
        settings.setFontTodayLabel(AppUtils.text);
        settings.setFontMonthAndYearMenuLabels(AppUtils.text);
        settings.setFontCalendarWeekdayLabels(AppUtils.text);

        calendarPanel = new CalendarPanel(settings);

        settings.setDateRangeLimits(LocalDate.now(), null);

        calendarPanel.setBorder(new LineBorder(Color.lightGray));
        mainPanel.add(calendarPanel);
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
