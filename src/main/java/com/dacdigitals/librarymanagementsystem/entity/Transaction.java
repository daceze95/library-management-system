package com.dacdigitals.librarymanagementsystem.entity;

import com.dacdigitals.librarymanagementsystem.entity.constant.TYPE;

import java.time.LocalDate;

public class Transaction {
    private long id;
    private long userId;
    private long bookId;
    private TYPE type;
    private LocalDate dueDate;
    private LocalDate trxnDate;
}
