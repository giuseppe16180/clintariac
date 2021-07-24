package com.clintariac.components.details.chat;


import java.awt.Component;
import java.util.function.Consumer;
import java.util.function.Supplier;
import com.clintariac.components.mvc.Controller;


public class ChatController implements Controller {

    private ChatModel model;
    private ChatView view;

    private Consumer<String> onMessageSelect;

    private Supplier<ChatModel> modelSupplier;

    public ChatController() {

        this.view = new ChatView();
        init();
    }

    public void updateView() {
        model = modelSupplier.get();
        view.getList().setModel(model.getChat());
    }

    /**
     * @param modelSupplier
     * @return ChatController
     */
    public ChatController setModelSupplier(Supplier<ChatModel> modelSupplier) {
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
     * @param onMessageSelect
     */
    public void addOnMessageSelect(Consumer<String> onMessageSelect) {
        this.onMessageSelect = onMessageSelect;
    }

    private void init() {

        view.getList().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && view.getList().getSelectedValue() != null) {
                messageSelect();
            }
        });
    }

    private void messageSelect() {
        onMessageSelect.accept(view.getList().getSelectedValue().getText());
    }
}
