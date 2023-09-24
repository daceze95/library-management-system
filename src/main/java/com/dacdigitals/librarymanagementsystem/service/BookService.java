package com.dacdigitals.librarymanagementsystem.service;

import com.dacdigitals.librarymanagementsystem.dto.BookDTO;
import com.dacdigitals.librarymanagementsystem.dto.BookUpdateDTO;
import com.dacdigitals.librarymanagementsystem.entity.Book;
import com.dacdigitals.librarymanagementsystem.exceptionHandler.NoBookFoundException;
import com.dacdigitals.librarymanagementsystem.exceptionHandler.NoContentException;
import com.dacdigitals.librarymanagementsystem.repository.IBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService implements IBookService {

    private final IBookRepository bookRepository;

    @Override
    public Book addBook(BookDTO book) {
        UUID uuid = UUID.randomUUID();
        LocalDateTime createdDate = LocalDateTime.now();
        Book bk = Book.build(0,
                book.getTitle(),
                book.getAuthor(),
                uuid,
                book.getCategory(),
                book.getYearOfPublication(),
                book.getDescription(),
                true, createdDate, createdDate);
        return bookRepository.save(bk);
    }

    @Override
    public List<Book> getAllBook() {

        List<Book> books = bookRepository.findAll();
        if (books.isEmpty()) {
            throw new NoContentException("No book found!");
        }
        return books;
    }

    @Override
    public Book updateBook(BookUpdateDTO book) {
        LocalDateTime updatedAt = LocalDateTime.now();
        Optional<Book> retrievedBook = bookRepository.findById(book.getId());
        if (retrievedBook.isPresent()) {
            Book book1 = retrievedBook.get();
            Book updatedBook = Book.build(book1.getId(), book.getTitle(),
                    book.getAuthor(),
                    book1.getISBN(), book.getCategory(),
                    book.getYearOfPublication(), book.getDescription(),
                    book.getAvailable(), book1.getDateCreated(), updatedAt);
            return bookRepository.save(updatedBook);
        } else {
            throw new NoBookFoundException("Book with id " + book.getId() +
                    " not found!");
        }
    }

    @Override
    public Book getBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);

        if (book.isPresent()) {
            return book.get();
        } else {
            throw new NoBookFoundException("Book with id " + id + " not " +
                    "found!");
        }
    }

    @Override
    public void deleteById(Long id) {
        Book res;
        Optional<Book> bk = bookRepository.findById(id);
        if (bk.isPresent()) {
            res = bk.get();
            bookRepository.delete(res);
        } else {
            throw new NoBookFoundException("Book with id number " + id +
                    " doesn't exist!");
        }
    }
}
