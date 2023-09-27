package com.dacdigitals.librarymanagementsystem.service;

import com.dacdigitals.librarymanagementsystem.dto.ReservationDTO;
import com.dacdigitals.librarymanagementsystem.entity.Reservation;

public interface IReservationService {
    Reservation makeReservation(ReservationDTO reserve);
}
