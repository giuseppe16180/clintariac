package com.clintariac.services.utils;

public class SingletonException extends RuntimeException {

    private static final long serialVersionUID = 4L;

    public SingletonException() {
        super("The class cannot be instantiated. An instance is already present");
    }
}
