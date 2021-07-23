package com.clintariac.components.details.chat;

import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import com.clintariac.components.details.chat.message.MessageModel;

public class ChatModel {

    private ListModel<MessageModel> chat;

    public ChatModel(List<MessageModel> chat) {
        DefaultListModel<MessageModel> tmp = new DefaultListModel<MessageModel>();
        tmp.addAll(chat);
        this.chat = tmp;
    }


    /**
     * @return ListModel<MessageModel>
     */
    public ListModel<MessageModel> getChat() {
        return chat;
    }


    /**
     * @param chat
     */
    public void setChat(ListModel<MessageModel> chat) {
        this.chat = chat;
    }
}
