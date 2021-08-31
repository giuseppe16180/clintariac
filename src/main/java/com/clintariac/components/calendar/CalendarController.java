package com.clintariac.components.calendar;

import java.awt.Component;
import java.time.LocalDate;
import java.util.function.Consumer;
import com.clintariac.components.mvc.Controller;
import com.clintariac.services.utils.Procedure;

/**
 * Calendar controller
 */

public class CalendarController implements Controller {

    private CalendarView view;
    private CalendarModel model;
    private Consumer<LocalDate> onDateSelect;
    private Procedure onAllSelect;

    /**
     * Costruttore di CalendarController, instanzia model e view del Calendar e inizializza gli
     * eventi.
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
     * Metodo che inizializza gli eventi del CalendarView, il click su una data nel pannello e il
     * click sul bottone di vista su più giorni.
     */
    private void init() {
        view.getCalendarPanel()
                .addCalendarListener((CalendarSelectDateListener) (e) -> dateSelect());
        view.getAllReserv().addActionListener((e) -> allSelect());
    }

    /**
     * Metodo da invocare alla selezione di una data nel selettore, invoca la funzione impostata
     * come {@code onDateSelect} dall'esterno e cambia la modalità di visualizzazione in "vista
     * singolo giorno".
     */
    private void dateSelect() {
        view.getAllReserv().setSelected(model.isAllView());
        if (!model.isAllView())
            onDateSelect.accept(view.getCalendarPanel().getSelectedDate());
        model.setAllView(false);
    }

    /**
     * Metodo per aggiungere dall'esterno una funzione da invocare in seguito alla selezione di una
     * data.
     * 
     * @param onDateSelect
     */
    public void addOnDateSelect(Consumer<LocalDate> onDateSelect) {
        this.onDateSelect = onDateSelect;
    }

    /**
     * Metodo da invocare in seguito alla selezione della checkbox per la vista su più giorni,
     * invoca la funzione impostata come {@code onAllSelect} dall'esterno e cambia la modalità di
     * visualizzazione in "vista su più giorni".
     */
    private void allSelect() {
        model.setAllView(true);
        view.getAllReserv().setSelected(model.isAllView());
        onAllSelect.run();
    }

    /**
     * Metodo per aggiungere dall'esterno una funzione da invocare in seguito alla selezione della
     * checkbox.
     * 
     * @param onAllSelect
     */
    public void addOnAllSelect(Procedure onAllSelect) {
        this.onAllSelect = onAllSelect;
    }
}
