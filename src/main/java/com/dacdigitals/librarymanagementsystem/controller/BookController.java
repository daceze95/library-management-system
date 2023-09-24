package com.dacdigitals.librarymanagementsystem.controller;

import com.dacdigitals.librarymanagementsystem.dto.BookDTO;
import com.dacdigitals.librarymanagementsystem.dto.BookUpdateDTO;
import com.dacdigitals.librarymanagementsystem.entity.Book;
import com.dacdigitals.librarymanagementsystem.service.IBookService;
import com.dacdigitals.librarymanagementsystem.utils.CustomApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final IBookService bookService;

    @PostMapping("/add")
    public CustomApiResponse<Book> addBook(@RequestBody @Valid BookDTO book) {
        Book bk = bookService.addBook(book);
        return new CustomApiResponse<>("Created successfully!", bk,
                HttpStatus.CREATED);
    }

    @GetMapping("/all-books")
    public CustomApiResponse<List<Book>> getBook() {
        List<Book> res = bookService.getAllBook();
        return new CustomApiResponse<>("Success", res, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public CustomApiResponse<Object> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return new CustomApiResponse<>("Success", book, HttpStatus.OK);
    }

    @PutMapping("/update-book")
    public CustomApiResponse<Object> updateBook(@RequestBody @Valid BookUpdateDTO book) {
        Book response = bookService.updateBook(book);
        return new CustomApiResponse<>("Success", response, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public CustomApiResponse<Object> deleteById(@PathVariable Long id) {
        bookService.deleteById(id);
        return new CustomApiResponse<>("Book with id number " + id + " has " +
                "been deleted successfully!", null,
                HttpStatus.OK);
    }

}
