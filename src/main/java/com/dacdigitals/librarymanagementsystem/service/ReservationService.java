/**
 * Reservation service not working as intended
 */

package com.dacdigitals.librarymanagementsystem.service;

import com.dacdigitals.librarymanagementsystem.dto.ReservationDTO;
import com.dacdigitals.librarymanagementsystem.entity.*;
import com.dacdigitals.librarymanagementsystem.entity.constant.TYPE;
import com.dacdigitals.librarymanagementsystem.exceptionHandler.BookNotAvailable;
import com.dacdigitals.librarymanagementsystem.exceptionHandler.ReservationNotFound;
import com.dacdigitals.librarymanagementsystem.repository.IBookAvailabilityRepository;
import com.dacdigitals.librarymanagementsystem.repository.IBookRepository;
import com.dacdigitals.librarymanagementsystem.repository.IReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService implements IReservationService {

    private final IPersonService ipersonService;
    private final IBookService iBookService;
    private final IReservationRepository iReservationRepository;
    private final IBookRepository iBookRepository;
    private final ITransactionService iTransactionService;
    private final IBookAvailabilityRepository bookAvailabilityRepository;

    @Override
    public Reservation makeReservation(ReservationDTO reservation) {

        Person user = ipersonService.getPerson(reservation.getUserId());
        Book book = iBookService.getBookById(reservation.getBookId());
        Transaction borrowedBook;

        if (user != null && book != null) {
            Optional<Transaction> transaction =
                    iTransactionService.getAllTransaction().stream().filter(trxn -> trxn.getBookId() == book.getId() && trxn.getType() == TYPE.BORROW).distinct().findFirst();

            if (transaction.isPresent()) {
                borrowedBook = transaction.get();
                //book shouldn't be available and not reserved
                if (!book.getAvailable() && !getReservationByBookId(book.getId()).isReserved()) {
                    LocalDateTime reserveDate = LocalDateTime.now();
                    LocalDateTime expiryDate =
                            borrowedBook.getDueDate().plusHours(1);

                    Reservation reserved = Reservation.builder()
                            .id(0)
                            .userId(user.getId())
                            .bookId(book.getId())
                            .isReserved(true)
                            .reserveDate(reserveDate)
                            .expiryDate(expiryDate)
                            .build();

                    BookAvailability setBookAvailability =
                            BookAvailability.builder()
                                    .id(0L)
                                    .userId(reserved.getUserId())
                                    .bookId(reserved.getBookId())
                                    .bookReserveId(reserved.getId())
                                    .isReserved(reserved.isReserved())
                                    .setTime(reserveDate)
                                    .updatedAt(reserveDate)
                                    .build();
                    bookAvailabilityRepository.save(setBookAvailability);

                    return iReservationRepository.save(reserved);
                } else {
                    throw new BookNotAvailable("Book not available! Check " + "back by " + borrowedBook.getDueDate());
                }
            }

        }
        return null;
    }


    @Override
    public Reservation getReservationByUserId(Long userId) {
        Reservation reservation = iReservationRepository.findByUserId(userId);
        if (reservation != null) {
            return reservation;
        } else {
            throw new ReservationNotFound("No reservation with user id " + userId + " was " + "found!");
        }
    }

    @Override
    public List<Reservation> getAllReservation() {
        return iReservationRepository.findAll();
    }

    @Override
    public Reservation getReservationByRservId(Long reservationId) {

        Optional<Reservation> reservations =
                iReservationRepository.findById(reservationId);
        if (reservations.isPresent()) {
            return reservations.get();
        } else {
            throw new ReservationNotFound("No reservation with id " + reservationId + " was " + "found!");
        }
    }

    @Override
    public Reservation getReservationByBookId(Long bookId) {
        Reservation reservation =
                iReservationRepository.findByBookId(bookId);
        if (reservation != null) {
            return reservation;
        } else {
            throw new ReservationNotFound("No reservation with book id " + bookId + " was " + "found! You can proceed to borrow the book.");
        }
    }

    @Override
    public String cancelReservation(Long id) {
        Reservation reservation = getReservationByRservId(id);

        if (reservation != null) {
            Optional<BookAvailability> bookAvailability =
                    bookAvailabilityRepository.findById(reservation.getId());
            if (bookAvailability.isPresent() && bookAvailability.get().isReserved()) {

                Book book = iBookService.getBookById(reservation.getBookId());
                book.setAvailable(true);
                book.setUpdatedAt(LocalDateTime.now());
                iBookRepository.save(book);
                reservation.setReserved(false);
                reservation.setExpiryDate(LocalDateTime.now());

                BookAvailability setBookAvailability =
                        BookAvailability.builder()
                                .id(bookAvailability.get().getId())
                                .userId(bookAvailability.get().getUserId())
                                .bookId(bookAvailability.get().getBookId())
                                .bookReserveId(reservation.getId())
                                .isReserved(false)
                                .setTime(bookAvailability.get().getSetTime())
                                .updatedAt(LocalDateTime.now())
                                .build();
                bookAvailabilityRepository.save(setBookAvailability);


                iReservationRepository.save(reservation);
                return "Reservation cancelled successfully!";
            } else {
                throw new BookNotAvailable("Book either not present or book " +
                        "isn't reserved!");
            }
        }
        return null;
    }

    @Override
    public String deleteReservation(Long id) {
        Optional<Reservation> reservation = iReservationRepository.findById(id);

        if (reservation.isPresent()) {
            Book book = iBookService.getBookById(reservation.get().getBookId());
            book.setAvailable(true);
            book.setUpdatedAt(LocalDateTime.now());
            iBookRepository.save(book);
            iReservationRepository.delete(reservation.get());
            return "Reservation deleted successfully!";
        } else {
            throw new ReservationNotFound("No reservation with id " + id + " was " + "found!");
        }
    }

}
