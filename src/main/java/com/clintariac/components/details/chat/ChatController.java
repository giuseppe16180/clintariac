package com.clintariac.components.details.chat;

import java.awt.Component;
import java.util.function.Supplier;
import com.clintariac.components.mvc.Controller;


public class ChatController implements Controller {

    private ChatModel model;
    private ChatView view;

    private int scrollPosition;

    private Supplier<ChatModel> modelSupplier;

    public ChatController() {

        this.view = new ChatView();
        init();
    }

    @Override
    public void reloadView() {
        model = modelSupplier.get();
        view.getList().setModel(model.getChat());
        view.scrollToBottom();
    }

    @Override
    public void updateView() {
        model = modelSupplier.get();
        if (view.getList().getPreferredSize().getHeight() == scrollPosition) {
            view.getList().setModel(model.getChat());
            view.scrollToBottom();
        } else {
            view.getList().setModel(model.getChat());
        }
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

    private void init() {
        view.scrollToBottom();
        view.getMainComponent().getVerticalScrollBar().addAdjustmentListener(e -> scroll());
    }

    private void scroll() {
        scrollPosition = (int) view.getMainComponent().getVerticalScrollBar().getValue();
    }
}
