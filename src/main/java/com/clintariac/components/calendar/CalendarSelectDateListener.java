package com.clintariac.components.calendar;

import com.github.lgooddatepicker.optionalusertools.CalendarListener;
import com.github.lgooddatepicker.zinternaltools.CalendarSelectionEvent;
import com.github.lgooddatepicker.zinternaltools.YearMonthChangeEvent;

/**
 * Interfaccia realizzata per poter definire l'evento {@code selectedDateChanged} tramite lambda
 */

@FunctionalInterface
interface CalendarSelectDateListener extends CalendarListener {

    @Override
    public void selectedDateChanged(CalendarSelectionEvent event);

    @Override
    default public void yearMonthChanged(YearMonthChangeEvent event) {};
}
