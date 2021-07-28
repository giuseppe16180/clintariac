package com.clintariac.components.userList;

import java.awt.Component;
import java.util.function.Consumer;
import java.util.function.Supplier;
import com.clintariac.components.mvc.Controller;

public class UsersListController implements Controller {

    private UsersListModel model;
    private UsersListView view;

    private Consumer<String> onUserSelect;

    private Supplier<UsersListModel> modelSupplier;

    public UsersListController() {

        this.view = new UsersListView();
        init();
    }

    public void fullUpdateView() {
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
     * @param onUserSelect
     */
    public void addOnUserSelect(Consumer<String> onUserSelect) {
        this.onUserSelect = onUserSelect;
    }

    private void init() {

        view.getList().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && view.getList().getSelectedValue() != null) {
                userSelect();
            }
        });
    }

    private void userSelect() {
        onUserSelect.accept(view.getList().getSelectedValue().getUserId());

    }
}
