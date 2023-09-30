package com.dacdigitals.librarymanagementsystem.exceptionHandler;

public class ReservationNotFound extends RuntimeException {
    public ReservationNotFound(String message) {
        super(message);
    }
}
