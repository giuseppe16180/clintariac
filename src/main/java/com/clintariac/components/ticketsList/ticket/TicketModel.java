package com.clintariac.components.ticketsList.ticket;


public class TicketModel {

    private String fullName;
    private String messagePreview;
    private String dateTime;
    private String userId;
    private String ticketId;
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }


    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }


    public TicketModel(String fullName, String messagePreview, String dateTime, String userId,
            String ticketId, boolean isSelected) {
        this.fullName = fullName;
        this.messagePreview = messagePreview;
        this.dateTime = dateTime;
        this.userId = userId;
        this.ticketId = ticketId;
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
    public String getMessagePreview() {
        return messagePreview;
    }


    /**
     * @param messagePreview
     */
    public void setMessagePreview(String messagePreview) {
        this.messagePreview = messagePreview;
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
}

