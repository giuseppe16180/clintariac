package com.clintariac.components.ticketsList;

import java.awt.Component;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import com.clintariac.components.mvc.View;
import com.clintariac.components.ticketsList.ticket.TicketModel;
import com.clintariac.components.ticketsList.ticket.TicketRenderer;
import com.clintariac.services.utils.AppUtils;

public class TicketsListView implements View {

    private JScrollPane mainPanel;
    private JList<TicketModel> list;

    public TicketsListView() {

        mainPanel = new JScrollPane();
        mainPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.setBackground(null);

        mainPanel.setBorder(AppUtils.createMainBorder("Notifiche"));

        list = new JList<>();
        list.setCellRenderer(new TicketRenderer());
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
     * @return JList<TicketModel>
     */
    public JList<TicketModel> getList() {
        return list;
    }
}
