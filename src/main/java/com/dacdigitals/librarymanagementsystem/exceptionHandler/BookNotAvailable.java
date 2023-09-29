package com.dacdigitals.librarymanagementsystem.exceptionHandler;

public class BookNotAvailable extends RuntimeException{
    public BookNotAvailable(String message) {
        super(message);
    }
}
