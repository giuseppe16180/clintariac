package com.clintariac.components.details.chat;

import java.awt.Component;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import com.clintariac.components.details.chat.message.MessageModel;
import com.clintariac.components.details.chat.message.MessageRenderer;
import com.clintariac.components.mvc.View;
import com.clintariac.services.utils.AppUtils;

public class ChatView implements View {

    private JScrollPane mainPanel;
    private JList<MessageModel> list;

    public ChatView() {

        mainPanel = new JScrollPane();
        mainPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.setBackground(null);

        mainPanel.setBorder(AppUtils.createMainBorder("Chat"));

        list = new JList<>();
        list.setCellRenderer(new MessageRenderer());
        list.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);

        mainPanel.setViewportView(list);
    }


    /**
     * @return Component
     */
    @Override
    public Component getMainComponent() {
        return this.mainPanel;
    }


    /**
     * @return JList<MessageModel>
     */
    public JList<MessageModel> getList() {
        return list;
    }
}
