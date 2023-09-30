package com.dacdigitals.librarymanagementsystem.service;

import com.dacdigitals.librarymanagementsystem.dto.BookDTO;
import com.dacdigitals.librarymanagementsystem.dto.BookUpdateDTO;
import com.dacdigitals.librarymanagementsystem.entity.Book;

import java.util.List;

public interface IBookService {
    Book addBook(BookDTO book);

    List<Book> getAllBook();

    Book updateBook(BookUpdateDTO book);

    Book getBookById(Long id);

    void deleteById(Long id);
}
