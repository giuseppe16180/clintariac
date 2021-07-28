package com.clintariac.components.details.chat;


import java.awt.Component;
import java.util.function.Supplier;
import com.clintariac.components.mvc.Controller;


public class ChatController implements Controller {

    private ChatModel model;
    private ChatView view;

    // private Consumer<String> onMessageSelect;

    private Supplier<ChatModel> modelSupplier;

    public ChatController() {

        this.view = new ChatView();
        init();
    }

    public void reloadView() {
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



    private void init() {
        view.scrollToBottom();

    }

}
