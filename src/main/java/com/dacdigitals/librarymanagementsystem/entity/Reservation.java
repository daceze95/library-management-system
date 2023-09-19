package com.dacdigitals.librarymanagementsystem.entity;

import java.time.LocalDate;

public class Reservation {
    private long id;
    private long userId;
    private long bookId;
    private LocalDate reserveDate;
    private LocalDate expiryDate;
}
