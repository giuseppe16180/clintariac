package com.clintariac.services.utils;

public class ListNotLoadedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 
     * @param message
     */
    public ListNotLoadedException(String message) {
        super(message);
    }
}
