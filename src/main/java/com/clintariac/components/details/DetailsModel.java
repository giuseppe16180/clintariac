package com.clintariac.components.details;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.clintariac.data.MessageData;

public class DetailsModel {

    /**
     * @return DetailsModel
     */
    public static DetailsModel empty() {
        return new Builder().build();
    }

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDateTime dateTime;
    private boolean isAwaiting;
    private String userId;
    private String ticketId;
    private String message;
    private List<MessageData> chat;

    public static class Builder {

        private String firstName;
        private String lastName;
        private String userId;
        private String ticketId;
        private String email;
        private String phone;
        private LocalDateTime dateTime;
        private boolean isAwaiting;
        private String message;
        private List<MessageData> chat;

        public Builder() {

            firstName = "";
            lastName = "";
            userId = "";
            ticketId = "";
            email = "";
            phone = "";
            dateTime = null;
            isAwaiting = false;
            message = "";
            chat = new ArrayList<MessageData>();
        }

        public DetailsModel build() {
            return new DetailsModel(firstName, lastName, email,
                    phone, dateTime, isAwaiting, userId, ticketId, message, chat);
        }

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withTicketId(String ticketId) {
            this.ticketId = ticketId;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder withDateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public Builder withAwaiting(boolean isAwaiting) {
            this.isAwaiting = isAwaiting;
            return this;
        }

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder withChat(List<MessageData> chat) {
            this.chat = chat;
            return this;
        }
    }



    /**
     * @return String
     */
    public String getFirstName() {
        return firstName;
    }


    /**
     * @return String
     */
    public String getLastName() {
        return lastName;
    }


    /**
     * @return String
     */
    public String getEmail() {
        return email;
    }


    /**
     * @return String
     */
    public String getPhone() {
        return phone;
    }


    /**
     * @return LocalDateTime
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }


    /**
     * @return TicketState
     */
    public boolean isAwaiting() {
        return isAwaiting;
    }


    /**
     * @return String
     */
    public String getUserId() {
        return userId;
    }


    /**
     * @return String
     */
    public String getTicketId() {
        return ticketId;
    }


    /**
     * @return String
     */
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @param dateTime
     */
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }


    /**
     * @param isAwaiting
     */
    public void setAwaiting(boolean isAwaiting) {
        this.isAwaiting = isAwaiting;
    }

    public List<MessageData> getChat() {
        return chat;
    }

    public void setChat(List<MessageData> chat) {
        this.chat = chat;
    }


    public DetailsModel(
            String firstName,
            String lastName,
            String email,
            String phone,
            LocalDateTime dateTime,
            boolean isAwaiting,
            String userId,
            String ticketId,
            String message,
            List<MessageData> chat) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.dateTime = dateTime;
        this.isAwaiting = isAwaiting;
        this.userId = userId;
        this.ticketId = ticketId;
        this.message = message;
        this.chat = chat;
    }


    /**
     * @return String
     */
    @Override
    public String toString() {
        return "DetailsModel [dateTime=" + dateTime + ", email=" + email + ", firstName="
                + firstName

                + ", lastName=" + lastName + ", phone=" + phone + ", state=" + isAwaiting
                + ", ticketId="
                + ticketId + ", userId=" + userId + "Message=" + message + "]";
    }

}

