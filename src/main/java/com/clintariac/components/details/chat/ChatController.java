package com.clintariac.components.details.chat;

import java.awt.Component;
import java.util.function.Supplier;
import com.clintariac.components.mvc.Controller;


public class ChatController implements Controller {

    private ChatModel model;
    private ChatView view;

    private int scrollPosition;

    private Supplier<ChatModel> modelSupplier;

    /**
     * Costruttore di ChatController istanzia la view e inizializza gli eventi. La sua view mostra
     * la lista dei messaggi contenuti nel suo model.
     */

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
     * Metodo per impostare il supplier del model.
     * 
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
     * Metodo per inizializzare gli eventi e lo scroll della chat.
     */

    private void init() {
        view.scrollToBottom();
        view.getMainComponent().getVerticalScrollBar().addAdjustmentListener(e -> scroll());
    }

    /**
     * Metodo per mantenere lo scroll della chat inalterato in seguito al reload della chat.
     */

    private void scroll() {
        scrollPosition = (int) view.getMainComponent().getVerticalScrollBar().getValue();
    }
}
