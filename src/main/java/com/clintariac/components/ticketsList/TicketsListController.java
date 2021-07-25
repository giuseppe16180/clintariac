package com.clintariac.components.ticketsList;

import java.awt.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import com.clintariac.components.mvc.Controller;

public class TicketsListController implements Controller {

    private TicketsListModel model;
    private TicketsListView view;

    private Supplier<TicketsListModel> modelSupplier;

    private BiConsumer<String, String> onTicketSelect;

    public TicketsListController() {

        this.view = new TicketsListView();
        init();
    }



    /**
     * @param modelSupplier
     * @return TicketsListController
     */
    public TicketsListController setModelSupplier(Supplier<TicketsListModel> modelSupplier) {
        this.modelSupplier = modelSupplier;
        return this;
    }

    public void updateView() {
        model = modelSupplier.get();
        view.getList().setModel(model.getTickets());
    }


    /**
     * @return Component
     */
    @Override
    public Component getView() {
        return this.view.getMainComponent();
    }

    private void init() {

        view.getList().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && view.getList().getSelectedValue() != null) {
                ticketSelect();
            }
        });
    }


    /**
     * @param onTicketSelect
     */
    public void addOnTicketSelect(BiConsumer<String, String> onTicketSelect) {
        this.onTicketSelect = onTicketSelect;
    }

    private void ticketSelect() {
        onTicketSelect.accept(
                view.getList().getSelectedValue().getTicketId(),
                view.getList().getSelectedValue().getUserId()); // TODO: da rivedere
                                                                // ancora
                                                                // era con il ticket

    }
}

