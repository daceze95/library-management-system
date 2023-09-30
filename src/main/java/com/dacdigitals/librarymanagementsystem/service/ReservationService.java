package com.dacdigitals.librarymanagementsystem.service;

import com.dacdigitals.librarymanagementsystem.dto.ReservationDTO;
import com.dacdigitals.librarymanagementsystem.entity.Book;
import com.dacdigitals.librarymanagementsystem.entity.Person;
import com.dacdigitals.librarymanagementsystem.entity.Reservation;
import com.dacdigitals.librarymanagementsystem.exceptionHandler.BookNotAvailable;
import com.dacdigitals.librarymanagementsystem.exceptionHandler.ReservationNotFound;
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

    @Override
    public Reservation makeReservation(ReservationDTO reservation) {

        Person user = ipersonService.getPerson(reservation.getUserId());
        Book book = iBookService.getBookById(reservation.getBookId());

        if (user != null && book != null) {
            if (!book.getAvailable()) {
                LocalDateTime reserveDate = LocalDateTime.now();
                LocalDateTime expiryDate = reserveDate.plusDays(7);

                Reservation reserved = Reservation.builder()
                        .id(0)
                        .userId(user.getId())
                        .bookId(book.getId())
                        .reserveDate(reserveDate)
                        .expiryDate(expiryDate)
                        .build();

                return iReservationRepository.save(reserved);
            } else {
                LocalDateTime expiryDate =
                        getReservationByBookId(reservation.getBookId()).getExpiryDate();
                throw new BookNotAvailable("Book not available! Check " + "back by " + expiryDate);
            }

        }
        return null;
    }

    @Override
    public String cancelReservation(Long id) {
        Optional<Reservation> reservation = iReservationRepository.findById(id);

        if (reservation.isPresent()) {
            Book book = iBookService.getBookById(reservation.get().getBookId());
            book.setAvailable(true);
            book.setUpdatedAt(LocalDateTime.now());
            iBookRepository.save(book);
            iReservationRepository.delete(reservation.get());
            return "Reservation cancelled successfully!";
        } else {
            throw new ReservationNotFound("No reservation with id " + id + " was " + "found!");
        }
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
        Reservation reservation = iReservationRepository.findByBookId(bookId);
        if (reservation != null) {
            return reservation;
        } else {
            throw new ReservationNotFound("No reservation with book id " + bookId + " was " + "found!");
        }
    }
}
