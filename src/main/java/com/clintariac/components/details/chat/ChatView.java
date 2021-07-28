package com.clintariac.components.details.chat;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import com.clintariac.components.details.chat.message.MessageModel;
import com.clintariac.components.details.chat.message.MessageRenderer;
import com.clintariac.components.mvc.View;
import com.clintariac.services.utils.AppUtils;

public class ChatView implements View {

    private JScrollPane mainPanel;
    private JList<MessageModel> list;

    public ChatView() {

        mainPanel = new JScrollPane();
        mainPanel.setBackground(null);

        mainPanel.setBorder(AppUtils.createMainBorder("Chat"));

        list = new JList<>();
        list.setCellRenderer(new MessageRenderer());
        list.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);

        mainPanel.setViewportView(list);
    }

    public void scrollToBottom() {
        SwingUtilities.invokeLater(() -> {
            int height = (int) list.getPreferredSize().getHeight();
            mainPanel.getVerticalScrollBar().setValue(height);
        });
    }


    /**
     * @return Component
     */
    @Override
    public JScrollPane getMainComponent() {
        return this.mainPanel;
    }


    /**
     * @return JList<MessageModel>
     */
    public JList<MessageModel> getList() {
        return list;
    }
}
