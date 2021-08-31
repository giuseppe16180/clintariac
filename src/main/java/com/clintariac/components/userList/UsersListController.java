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


    /**
     * 
     * Il costruttore di UserListController instanzia la view e inizializza gli ascoltatori
     * attraverso il metodo {@code init()}.
     */
    public UsersListController() {

        this.view = new UsersListView();
        init();
    }

    @Override
    public void reloadView() {
        model = modelSupplier.get();
        view.getList().setModel(model.getUsers());
    }

    /**
     * 
     * Metodo per definire il supplier del modello di UserList dall'esterno, attraverso un
     * {@code Supplier<UserListModel>} ricevuto a parametro.
     * 
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
     * * Metodo per definire dall'esterno la funzione da chiamare in seguito alla procedura di
     * selezione di un utente.
     * 
     * @param onUserSelect
     */
    public void addOnUserSelect(Consumer<String> onUserSelect) {
        this.onUserSelect = onUserSelect;
    }

    /**
     * Metodo per inizializzare gli ascoltatori
     */
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
