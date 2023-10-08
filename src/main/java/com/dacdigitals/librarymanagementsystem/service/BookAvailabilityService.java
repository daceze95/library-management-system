package com.dacdigitals.librarymanagementsystem.service;

import com.dacdigitals.librarymanagementsystem.entity.Book;
import com.dacdigitals.librarymanagementsystem.entity.BookAvailability;
import com.dacdigitals.librarymanagementsystem.exceptionHandler.BookNotAvailable;
import com.dacdigitals.librarymanagementsystem.repository.IBookAvailabilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookAvailabilityService implements IBookAvailability{

    private final IBookAvailabilityRepository bookAvailabilityRepository;
    private final IBookService bookService;
    @Override
    public BookAvailability findByBookId(Long bookId) {
        Book book = bookService.getBookById(bookId);

        if(book != null){
            try{

                BookAvailability bookAvailable =
                        bookAvailabilityRepository.findByBookId(book.getId());
            }catch (Exception e){
                throw new BookNotAvailable(e.getMessage());
            }
        }
        return null;
    }
}
