package com.clintariac.data;

public class EmailData {

    public final String address;
    public final String subject;
    public final String message;

    public EmailData(String address, String subject, String message) {
        this.address = address;
        this.subject = subject;
        this.message = message;
    }


    /**
     * @return String
     */
    @Override
    public String toString() {
        return "EmailData [address=" + address + ", message=" + message + ", subject=" + subject
                + "]";
    }
}
