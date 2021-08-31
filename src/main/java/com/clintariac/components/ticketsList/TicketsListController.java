package com.clintariac.components.ticketsList;

import java.awt.Component;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import com.clintariac.components.mvc.Controller;

public class TicketsListController implements Controller {

    private TicketsListModel model;
    private TicketsListView view;

    private Supplier<TicketsListModel> modelSupplier;

    private BiConsumer<String, String> onTicketSelect;


    /**
     * 
     * Costruttore di TicketsListController che istanzia la view e inizializza gli ascoltatori.
     */
    public TicketsListController() {

        this.view = new TicketsListView();
        init();
    }



    /**
     * Metodo per definire il supplier del modello di TicketList dall'esterno, attraverso un
     * {@code Supplier<TicketsListModel>} ricevuto a parametro.
     * 
     * @param modelSupplier
     * @return TicketsListController
     */
    public TicketsListController setModelSupplier(Supplier<TicketsListModel> modelSupplier) {
        this.modelSupplier = modelSupplier;
        return this;
    }

    @Override
    public void reloadView() {
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


    /**
     * Metodo per inizializzare l'ascoltatore per la selezione di un ticket.
     */
    private void init() {
        view.getList().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && view.getList().getSelectedValue() != null) {
                ticketSelect();
            }
        });
    }


    /**
     * * Metodo per definire dall'esterno la funzione da chiamare in seguito alla procedura di
     * selezione di un ticket.
     * 
     * @param onTicketSelect
     */
    public void addOnTicketSelect(BiConsumer<String, String> onTicketSelect) {
        this.onTicketSelect = onTicketSelect;
    }

    private void ticketSelect() {
        onTicketSelect.accept(
                view.getList().getSelectedValue().getTicketId(),
                view.getList().getSelectedValue().getUserId());
    }
}

