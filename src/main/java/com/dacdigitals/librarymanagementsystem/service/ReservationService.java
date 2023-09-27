package com.dacdigitals.librarymanagementsystem.service;

import com.dacdigitals.librarymanagementsystem.dto.ReservationDTO;
import com.dacdigitals.librarymanagementsystem.entity.Book;
import com.dacdigitals.librarymanagementsystem.entity.Person;
import com.dacdigitals.librarymanagementsystem.entity.Reservation;
import com.dacdigitals.librarymanagementsystem.exceptionHandler.NoBookFoundException;
import com.dacdigitals.librarymanagementsystem.exceptionHandler.NoPersonFoundException;
import com.dacdigitals.librarymanagementsystem.repository.IBookRepository;
import com.dacdigitals.librarymanagementsystem.repository.IReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

        if (user != null) {
            if (book != null && book.getAvailable()) {
                LocalDateTime reserveDate = LocalDateTime.now();
                LocalDateTime expiryDate = reserveDate.plusDays(7);
                boolean changeAvailStatus = !book.getAvailable();

                Reservation reserved = Reservation.builder()
                        .id(0)
                        .userId(user.getId())
                        .bookId(book.getId())
                        .reserveDate(reserveDate)
                        .expiryDate(expiryDate)
                        .build();

                Book availableStatus = Book.builder()
                        .id(book.getId())
                        .title(book.getTitle())
                        .author(book.getAuthor())
                        .ISBN(book.getISBN())
                        .category(book.getCategory())
                        .yearOfPublication(book.getYearOfPublication())
                        .description(book.getDescription())
                        .available(changeAvailStatus)
                        .dateCreated(book.getDateCreated())
                        .updatedAt(reserveDate)
                        .build();
                iBookRepository.save(availableStatus);
                return iReservationRepository.save(reserved);
            } else {
                throw new NoBookFoundException("Book with id " + reservation.getBookId() + " not " +
                        "found!");
            }
        } else {
            throw new NoPersonFoundException("Person with id number " + reservation.getUserId() +
                    " not found!");
        }
    }
}
