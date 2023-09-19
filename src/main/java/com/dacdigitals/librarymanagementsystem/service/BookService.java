package com.dacdigitals.librarymanagementsystem.service;

import com.dacdigitals.librarymanagementsystem.dto.BookDTO;
import com.dacdigitals.librarymanagementsystem.entity.Book;
import com.dacdigitals.librarymanagementsystem.repository.IBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        Book bk = Book.build(0,
                book.getTitle(),
                book.getAuthor(),
                uuid,
                book.getCategory(),
                book.getYearOfPublication(),
                book.getDescription(),
                true);
        return bookRepository.save(bk);
    }

    @Override
    public List<Book> getBook() {
        return bookRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        Book res;
        Optional<Book> bk = bookRepository.findById(id);
        if(bk.isPresent()){
          res =  bk.get();
            bookRepository.delete(res);
            return;
        }
    }
}
