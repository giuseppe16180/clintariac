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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isUserSent() {
        return isUserSent;
    }

    public void setUserSent(boolean isUserSent) {
        this.isUserSent = isUserSent;
    }
}
