package com.clintariac.services.utils;

public class UserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 2L;

    /**
     * 
     * @param message
     */
    public UserNotFoundException(String message) {
        super("No user for " + message);
    }
}
