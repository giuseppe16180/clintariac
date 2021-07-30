package com.clintariac.components.reservationsList.reservation;

import com.clintariac.data.TicketState;

public class ReservationModel {

    private String fullName;
    private String dateTime;
    private String userId;
    private String ticketId;
    private TicketState state;
    private boolean isSelected;

    public ReservationModel(
            String fullName,
            String dateTime,
            String userId,
            String ticketId,
            TicketState state,
            boolean isSelected) {

        this.fullName = fullName;
        this.dateTime = dateTime;
        this.userId = userId;
        this.ticketId = ticketId;
        this.state = state;
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }


    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    /**
     * @return String
     */
    public String getFullName() {
        return fullName;
    }


    /**
     * @param fullName
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    /**
     * @return String
     */
    public String getDateTime() {
        return dateTime;
    }


    /**
     * @param dateTime
     */
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }


    /**
     * @return String
     */
    public String getUserId() {
        return userId;
    }


    /**
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }


    /**
     * @return String
     */
    public String getTicketId() {
        return ticketId;
    }


    /**
     * @param ticketId
     */
    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }


    /**
     * @return TicketState
     */
    public TicketState getState() {
        return state;
    }


    /**
     * @param state
     */
    public void setState(TicketState state) {
        this.state = state;
    }
}
