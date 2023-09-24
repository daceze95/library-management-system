package com.dacdigitals.librarymanagementsystem.exceptionHandler;

public class NoBookFoundException extends RuntimeException{
    public NoBookFoundException(String message) {
        super(message);
    }
}
