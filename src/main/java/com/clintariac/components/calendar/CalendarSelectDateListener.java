package com.clintariac.components.calendar;

import com.github.lgooddatepicker.optionalusertools.CalendarListener;
import com.github.lgooddatepicker.zinternaltools.CalendarSelectionEvent;
import com.github.lgooddatepicker.zinternaltools.YearMonthChangeEvent;

interface CalendarSelectDateListener extends CalendarListener {

    @Override
    public void selectedDateChanged(CalendarSelectionEvent event);

    @Override
    default public void yearMonthChanged(YearMonthChangeEvent event) {
    };
}
