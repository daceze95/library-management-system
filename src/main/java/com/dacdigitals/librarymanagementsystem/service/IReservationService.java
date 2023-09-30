package com.dacdigitals.librarymanagementsystem.service;

import com.dacdigitals.librarymanagementsystem.dto.ReservationDTO;
import com.dacdigitals.librarymanagementsystem.entity.Reservation;

import java.util.List;

public interface IReservationService {
    Reservation makeReservation(ReservationDTO reserve);

    String cancelReservation( Long id);

    Reservation getReservationByUserId(Long userId);

    List<Reservation> getAllReservation();

    Reservation getReservationByRservId(Long id);

    Reservation getReservationByBookId(Long bookId);
}
