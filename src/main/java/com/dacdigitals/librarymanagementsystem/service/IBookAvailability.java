package com.dacdigitals.librarymanagementsystem.service;

import com.dacdigitals.librarymanagementsystem.entity.BookAvailability;

public interface IBookAvailability {
    BookAvailability findByBookId(Long bookId);
}
