package com.clintariac.components.ticketsList;

import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import com.clintariac.components.ticketsList.ticket.TicketModel;

public class TicketsListModel {

    private ListModel<TicketModel> tickets;

    public TicketsListModel(List<TicketModel> tickets) {
        DefaultListModel<TicketModel> t = new DefaultListModel<TicketModel>();
        t.addAll(tickets);
        this.tickets = t;
    }


    /**
     * @return ListModel<TicketModel>
     */
    public ListModel<TicketModel> getTickets() {
        return tickets;
    }


    /**
     * @param tickets
     */
    public void setTickets(ListModel<TicketModel> tickets) {
        this.tickets = tickets;
    }
}

