package com.clintariac.components.details.chat.message;

public class MessageModel {

    private String text;
    private String dateTime;
    private boolean isUserSent;

    public MessageModel(String text, String dateTime, boolean isUserSent) {
        this.text = text;
        this.dateTime = dateTime;
        this.isUserSent = isUserSent;
    }


    /**
     * @return String
     */
    public String getText() {
        return text;
    }


    /**
     * @param text
     */
    public void setText(String text) {
        this.text = text;
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
     * @return boolean
     */
    public boolean isUserSent() {
        return isUserSent;
    }


    /**
     * @param isUserSent
     */
    public void setUserSent(boolean isUserSent) {
        this.isUserSent = isUserSent;
    }
}
