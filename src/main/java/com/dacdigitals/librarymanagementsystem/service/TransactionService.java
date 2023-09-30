package com.dacdigitals.librarymanagementsystem.service;

import com.dacdigitals.librarymanagementsystem.dto.TransactionDTO;
import com.dacdigitals.librarymanagementsystem.entity.Book;
import com.dacdigitals.librarymanagementsystem.entity.Person;
import com.dacdigitals.librarymanagementsystem.entity.Reservation;
import com.dacdigitals.librarymanagementsystem.entity.Transaction;
import com.dacdigitals.librarymanagementsystem.entity.constant.TYPE;
import com.dacdigitals.librarymanagementsystem.exceptionHandler.BookNotAvailable;
import com.dacdigitals.librarymanagementsystem.exceptionHandler.ReservationNotFound;
import com.dacdigitals.librarymanagementsystem.repository.IBookRepository;
import com.dacdigitals.librarymanagementsystem.repository.ITransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService implements ITransactionService {

    private final IBookService bookService;
    private final IPersonService personService;
    private final ITransactionRepository transactionRepository;
    private final IBookRepository iBookRepository;


    @Override
    public Transaction borrowBook(TransactionDTO transaction) {
        Book book = bookService.getBookById(transaction.getBookId());
        Person person = personService.getPerson(transaction.getUserId());
        if (book != null && person != null) {
            if (book.getAvailable()) {
                LocalDateTime trxnDate = LocalDateTime.now();
                LocalDateTime dueDate = trxnDate.plusDays(7);
                boolean changeAvailStatus = false;

                Transaction bookBorrowed = Transaction.builder()
                        .id(0)
                        .bookId(book.getId())
                        .userId(person.getId())
                        .type(TYPE.BORROW)
                        .trxnDate(trxnDate)
                        .dueDate(dueDate)
                        .returnDate(null)
                        .build();

//                update book status
                book.setAvailable(changeAvailStatus);
                book.setUpdatedAt(trxnDate);
                iBookRepository.save(book);

                return transactionRepository.save(bookBorrowed);
            } else {
                LocalDateTime expiryDate =
                        getTransactionByBookId(transaction.getBookId()).getDueDate();
                throw new BookNotAvailable("Book not available! Check " + "back by " + expiryDate);
            }
        }
        return null;
    }

    @Override
    public Transaction returnBook(Long transactionId) {

        Transaction transaction = getTransactionById(transactionId);
        Book book = bookService.getBookById(transaction.getBookId());
        Person person = personService.getPerson(transaction.getUserId());

        if (book != null && person != null) {
            if (!book.getAvailable() && person.getId() == transaction.getUserId()) {
                LocalDateTime trxnDate = LocalDateTime.now();
                Transaction bookReturned = Transaction.builder()
                        .id(transaction.getId())
                        .bookId(book.getId())
                        .userId(person.getId())
                        .type(TYPE.RETURN)
                        .trxnDate(transaction.getTrxnDate())
                        .dueDate(transaction.getDueDate())
                        .returnDate(trxnDate)
                        .build();
                return transactionRepository.save(bookReturned);
            } else {
                throw new BookNotAvailable("Sorry, no book to return at the " +
                        "moment!");
            }
        }
        return null;
    }

    @Override
    public String cancelBorrow(Long transactionId) {
        Optional<Transaction> cancelTrxn =
                transactionRepository.findById(transactionId);

        if (cancelTrxn.isPresent()) {
            Book book = bookService.getBookById(cancelTrxn.get().getBookId());
            book.setAvailable(true);
            book.setUpdatedAt(LocalDateTime.now());
            transactionRepository.delete(cancelTrxn.get());
            return "Transaction cancelled successfully!";
        } else {
            throw new ReservationNotFound("No borrowed book with id " + transactionId +
                    " " +
                    "was " +
                    "found!");
        }
    }

    @Override
    public Transaction getTransactionByBookId(Long bookId) {
        Transaction transaction = transactionRepository.findByBookId(bookId);
        if (transaction != null) {
            return transaction;
        } else {
            throw new ReservationNotFound("No transaction with book id " + bookId +
                    " was " + "found!");
        }
    }

    @Override
    public Transaction getTransactionById(Long transactionId) {

        Optional<Transaction> transaction =
                transactionRepository.findById(transactionId);
        if (transaction.isPresent()) {
            return transaction.get();
        } else {
            throw new ReservationNotFound("No transaction with id " + transactionId +
                    " was " + "found!");
        }
    }

    @Override
    public List<Transaction> getAllTransaction() {
        return transactionRepository.findAll();
    }
}
