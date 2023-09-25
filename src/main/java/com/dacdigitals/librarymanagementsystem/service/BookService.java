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
        boolean isAvailable = true;
        LocalDateTime createdDate = LocalDateTime.now();
        Book bk = Book.builder()
                .id(0)
                .title(book.getTitle())
                .author(book.getAuthor())
                .ISBN(uuid)
                .category(book.getCategory())
                .yearOfPublication(book.getYearOfPublication())
                .description(book.getDescription())
                .available(isAvailable)
                .dateCreated(createdDate)
                .updatedAt(createdDate)
                .build();
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
            Book updatedBook = Book.builder()
                    .id(book1.getId())
                    .title(book.getTitle())
                    .author(book.getAuthor())
                    .ISBN(book1.getISBN())
                    .category(book.getCategory())
                    .yearOfPublication(book.getYearOfPublication())
                    .description(book.getDescription())
                    .available(book.getAvailable())
                    .dateCreated(book1.getDateCreated())
                    .updatedAt(updatedAt)
                    .build();
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
