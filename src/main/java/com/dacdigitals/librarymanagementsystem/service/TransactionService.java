/**
 *  Transaction service not working as intended
 *  fix borrow book exception expiry time
 */

package com.dacdigitals.librarymanagementsystem.service;

import com.dacdigitals.librarymanagementsystem.dto.TransactionDTO;
import com.dacdigitals.librarymanagementsystem.entity.*;
import com.dacdigitals.librarymanagementsystem.entity.constant.TYPE;
import com.dacdigitals.librarymanagementsystem.exceptionHandler.BookNotAvailable;
import com.dacdigitals.librarymanagementsystem.exceptionHandler.ReservationNotFound;
import com.dacdigitals.librarymanagementsystem.repository.IBookAvailabilityRepository;
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
    private final IBookAvailabilityRepository bookAvailabilityRepository;


    @Override
    public Transaction borrowBook(TransactionDTO transaction) {
        Book book = bookService.getBookById(transaction.getBookId());
        Person person = personService.getPerson(transaction.getUserId());

        if (book != null && person != null) {
//            To borrow, book must be available and reserved period is expired
//            or not reserved
            List<BookAvailability> bAStatus =
                    bookAvailabilityRepository.findAll().stream().filter(ba -> ba.getBookId() == book.getId() && ba.isReserved()).toList();

            if (book.getAvailable() && bAStatus.isEmpty()) {
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
                //Book is reserved, and the reservation is still valid
//                LocalDateTime expiryDate = reserve.getExpiryDate();
                throw new BookNotAvailable("Book is reserved until " );
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

// After return, book status should be true if and only if that book isn't
// reserved else false
                book.setAvailable(true);
                book.setUpdatedAt(LocalDateTime.now());
                iBookRepository.save(book);

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
