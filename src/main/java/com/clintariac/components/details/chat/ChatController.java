package com.clintariac.components.details.chat;


import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.swing.JScrollBar;
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
        view.scrollToBottom();
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

    public void scrollToBottom() {
        view.scrollToBottom();
    }

    /**
     * 
     * @param onMessageSelect
     */
    public void addOnMessageSelect(Consumer<String> onMessageSelect) {
        this.onMessageSelect = onMessageSelect;
    }

    private void init() {
        view.scrollToBottom();

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
