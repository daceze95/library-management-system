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
            if (book.getAvailable()) {
                LocalDateTime reserveDate = LocalDateTime.now();
                LocalDateTime expiryDate = reserveDate.plusDays(7);
                boolean changeAvailStatus = false;

                Reservation reserved = Reservation.builder()
                        .id(0)
                        .userId(user.getId())
                        .bookId(book.getId())
                        .reserveDate(reserveDate)
                        .expiryDate(expiryDate)
                        .build();

                //update book status
                book.setAvailable(changeAvailStatus);
                book.setUpdatedAt(reserveDate);
                iBookRepository.save(book);

                return iReservationRepository.save(reserved);
            } else {
                Optional<Reservation> res =
                        iReservationRepository.findAll().stream().filter(rsv -> rsv.getBookId() == reservation.getBookId()).findFirst();
                if (res.isPresent()) {
                    Reservation result = res.get();
                    throw new BookNotAvailable("Book not available! Check " +
                            "back by " + result.getExpiryDate());
                }
            }

        }
        return null;
    }

    @Override
    public String cancelReservation() {
        return null;
    }

    @Override
    public List<Reservation> getReservationByUserId(Long userId) {
        Person person = ipersonService.getPerson(userId);

        List<Reservation> reservations =
                iReservationRepository.findAll().stream().filter(reserved -> reserved.getUserId() == person.getId()).toList();
        if (!reservations.isEmpty()) {
            return reservations;
        } else {
            throw new ReservationNotFound("No reservation with user id " + userId + " was " + "found!");
        }

    }

    @Override
    public List<Reservation> getAllReservation() {
        return iReservationRepository.findAll();
    }
}
