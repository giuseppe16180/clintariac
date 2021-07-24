package com.clintariac.components.calendar;

import java.awt.Component;
import java.time.LocalDate;
import java.util.function.Consumer;
import com.clintariac.components.mvc.Controller;

/**
 * Calendar controller
 * 
 * Classe che gestisce l'evento per selezionare una data nel
 * {@code CalendarPanel}.
 */

public class CalendarController implements Controller {

    private CalendarView view;
    private Consumer<LocalDate> onDateSelect;
    private Consumer<LocalDate> onAllSelect;

    /**
     * Costruttore di CalendarController, instanzia model e view del Calendar e
     * aggiunge l'evento.
     */
    public CalendarController() {

        this.view = new CalendarView();
        view.getCalendarPanel().setSelectedDate(LocalDate.now());
        init();
    }

    /**
     * @return Component
     */
    @Override
    public Component getView() {
        return this.view.getMainComponent();
    }

    /**
     * Metodo che permette di aggiungere un ascoltatore al CalendarPanel.
     */
    private void init() {
        view.getCalendarPanel().addCalendarListener((CalendarSelectDateListener) (e) -> dateSelect());
    }

    /**
     * 
     */
    private void dateSelect() {
        onDateSelect.accept(view.getCalendarPanel().getSelectedDate());
    }

    /**
     * @param onDateSelect
     */
    public void addOnDateSelect(Consumer<LocalDate> onDateSelect) {
        this.onDateSelect = onDateSelect;
    }

    /**
     * 
     */
    private void allSelect() {
        onAllSelect.accept(LocalDate.now());
    }

    /**
     * @param onDateSelect
     */
    public void addAllSelect(Consumer<LocalDate> onAllSelect) {
        this.onAllSelect = onAllSelect;
    }
}
