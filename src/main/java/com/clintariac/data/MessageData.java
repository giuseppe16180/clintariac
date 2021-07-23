package com.clintariac.data;

import java.time.LocalDateTime;

public class MessageData {

    public final String text;
    public final LocalDateTime dateTime;
    public final boolean isUserSent;

    public MessageData(String text, LocalDateTime dateTime, boolean isUserSent) {
        this.text = text;
        this.dateTime = dateTime;
        this.isUserSent = isUserSent;
    }
}
