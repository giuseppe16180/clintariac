package com.clintariac.components.details;

import java.time.LocalDateTime;

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
        }

        public DetailsModel build() {
            return new DetailsModel(firstName, lastName, email,
                    phone, dateTime, isAwaiting, userId, ticketId, message);
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


    public DetailsModel(
            String firstName,
            String lastName,
            String email,
            String phone,
            LocalDateTime dateTime,
            boolean isAwaiting,
            String userId,
            String ticketId,
            String message) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.dateTime = dateTime;
        this.isAwaiting = isAwaiting;
        this.userId = userId;
        this.ticketId = ticketId;
        this.message = message;
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

