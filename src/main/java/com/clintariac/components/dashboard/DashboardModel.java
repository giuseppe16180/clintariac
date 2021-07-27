package com.clintariac.components.dashboard;

import java.time.LocalDate;
import com.clintariac.data.TicketData;

public class DashboardModel {

    private LocalDate selectedDate;
    private TicketData ticket;
    private String selectedUser;
    private String selectedTicket;
    private boolean isUserSelected;
    private boolean isTicketSelected;
    private boolean isDayView;

    public DashboardModel() {
        this.selectedDate = LocalDate.now();
        this.selectedUser = "";
        this.selectedTicket = "";
        this.isUserSelected = false;
        this.isTicketSelected = false;
        this.isDayView = true;
    }

    public TicketData getTicket() {
        return ticket;
    }


    public void setTicket(TicketData ticket) {
        this.ticket = ticket;
    }


    public boolean isDayView() {
        return isDayView;
    }


    public void setDayView(boolean isDayView) {
        this.isDayView = isDayView;
    }

    /**
     * @return LocalDate
     */
    public LocalDate getSelectedDate() {
        return selectedDate;
    }


    /**
     * @param selectedDate
     */
    public void setSelectedDate(LocalDate selectedDate) {
        this.selectedDate = selectedDate;
    }


    /**
     * @return String
     */
    public String getSelectedUser() {
        return selectedUser;
    }


    /**
     * @param selectedUser
     */
    public void setSelectedUser(String selectedUser) {
        this.selectedUser = selectedUser;
        this.isUserSelected = true;
    }


    /**
     * @return boolean
     */
    public boolean isUserSelected() {
        return isUserSelected;
    }

    public void unselectUser() {
        this.isUserSelected = false;
        this.selectedUser = "";
    }

    /**
     * @return String
     */
    public String getSelectedTicket() {
        return selectedTicket;
    }


    /**
     * @param selectedTicket
     */
    public void setSelectedTicket(String selectedTicket) {
        this.selectedTicket = selectedTicket;
        this.isTicketSelected = true;
    }


    /**
     * @return boolean
     */
    public boolean isTicketSelected() {
        return isTicketSelected;
    }

    public void unselectTicket() {
        this.isTicketSelected = false;
        this.selectedTicket = "";
    }

}
