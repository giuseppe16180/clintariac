package com.clintariac.components.userList;

import java.awt.Component;
import java.util.function.Consumer;
import java.util.function.Supplier;
import com.clintariac.components.mvc.Controller;

public class UsersListController implements Controller {

    private UsersListModel model;
    private UsersListView view;

    private Consumer<String> onTicketSelect;

    private Supplier<UsersListModel> modelSupplier;

    public UsersListController() {

        this.view = new UsersListView();
        init();
    }

    public void updateView() {
        model = modelSupplier.get();
        view.getList().setModel(model.getUsers());
    }

    /**
     * @param modelSupplier
     * @return UsersListController
     */
    public UsersListController setModelSupplier(Supplier<UsersListModel> modelSupplier) {
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
        onTicketSelect.accept(view.getList().getSelectedValue().getUserId());

    }
}
