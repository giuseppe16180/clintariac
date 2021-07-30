package com.clintariac.components.calendar;

import java.awt.Component;
import java.time.LocalDate;
import java.util.function.Consumer;
import com.clintariac.components.mvc.Controller;

/**
 * Calendar controller
 * 
 * Classe che gestisce l'evento per selezionare una data nel {@code CalendarPanel}.
 */

public class CalendarController implements Controller {

    private CalendarView view;
    private CalendarModel model;
    private Consumer<LocalDate> onDateSelect;
    private Consumer<LocalDate> onAllSelect;

    /**
     * Costruttore di CalendarController, instanzia model e view del Calendar e aggiunge l'evento.
     */
    public CalendarController() {

        this.view = new CalendarView();
        this.model = new CalendarModel();
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

    @Override
    public void reloadView() {
        view.getCalendarPanel().setSelectedDate(LocalDate.now());
    }

    /**
     * Metodo che permette di aggiungere un ascoltatore al CalendarPanel.
     */
    private void init() {
        view.getCalendarPanel()
                .addCalendarListener((CalendarSelectDateListener) (e) -> dateSelect());
        view.getAllReserv().addActionListener((e) -> allSelect());
    }

    /**
     * 
     */
    private void dateSelect() {
        view.getAllReserv().setSelected(model.isAllView());
        if (!model.isAllView())
            onDateSelect.accept(view.getCalendarPanel().getSelectedDate());
        model.setAllView(false);
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
        model.setAllView(true);
        view.getAllReserv().setSelected(model.isAllView());
        onAllSelect.accept(LocalDate.now());
    }

    /**
     * @param onDateSelect
     */
    public void addOnAllSelect(Consumer<LocalDate> onAllSelect) {
        this.onAllSelect = onAllSelect;
    }
}
