package com.clintariac.components.dashboard;

// import java.time.LocalDate;

// public class DashboardModel {

// private LocalDate selectedDate;
// private String selectedTicket;
// private boolean isTicketSelected;

// public DashboardModel() {
// this.selectedDate = LocalDate.now();
// this.selectedTicket = null;
// this.isTicketSelected = false;
// }


// /**
// * @return LocalDate
// */
// public LocalDate getSelectedDate() {
// return selectedDate;
// }


// /**
// * @param selectedDate
// */
// public void setSelectedDate(LocalDate selectedDate) {
// this.selectedDate = selectedDate;
// }


// /**
// * @return String
// */
// public String getSelectedTicket() {
// return selectedTicket;
// }


// /**
// * @param selectedTicket
// */
// public void setSelectedTicket(String selectedTicket) {
// this.selectedTicket = selectedTicket;
// this.isTicketSelected = true;
// }


// /**
// * @return boolean
// */
// public boolean isTicketSelected() {
// return isTicketSelected;
// }

// public void unselectTicket() {
// this.isTicketSelected = false;
// this.selectedTicket = null;
// }
// }



import java.time.LocalDate;

public class DashboardModel {

    private LocalDate selectedDate;
    private String selectedUser;
    private boolean isUserSelected;

    public DashboardModel() {
        this.selectedDate = LocalDate.now();
        this.selectedUser = null;
        this.isUserSelected = false;
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
        this.selectedUser = null;
    }
}
