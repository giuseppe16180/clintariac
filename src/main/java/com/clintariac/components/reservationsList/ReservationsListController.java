package com.clintariac.components.reservationsList;

import java.awt.Component;
import java.util.function.Consumer;
import java.util.function.Supplier;
import com.clintariac.components.mvc.Controller;


public class ReservationsListController implements Controller {

    private ReservationsListModel model;
    private ReservationsListView view;

    private Consumer<String> onTicketSelect;

    private Supplier<ReservationsListModel> modelSupplier;

    public ReservationsListController() {

        this.view = new ReservationsListView();
        init();
    }

    public void updateView() {
        model = modelSupplier.get();
        view.getList().setModel(model.getReservations());
    }

    /**
     * @param modelSupplier
     * @return ReservationsListController
     */
    public ReservationsListController setModelSupplier(
            Supplier<ReservationsListModel> modelSupplier) {
        this.modelSupplier = modelSupplier;
        return this;
    }

    /**
     * @return Component
     */
    @Override
    public Component getView() {
        return this.view.getMainComponent();
    }

    /**
     * 
     * @param onTicketSelect
     */
    public void addOnTicketSelect(Consumer<String> onTicketSelect) {
        this.onTicketSelect = onTicketSelect;
    }

    private void init() {

        view.getList().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && view.getList().getSelectedValue() != null) {
                ticketSelect();
            }
        });
    }

    private void ticketSelect() {
        onTicketSelect.accept(view.getList().getSelectedValue().getTicketId());

    }
}
