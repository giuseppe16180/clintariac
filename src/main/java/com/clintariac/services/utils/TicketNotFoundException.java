package com.clintariac.services.utils;

public class TicketNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 3L;

    /**
     * @param message
     */
    public TicketNotFoundException(String message) {
        super("No ticket for " + message);
    }
}
