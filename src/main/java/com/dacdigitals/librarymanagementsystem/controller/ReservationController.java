package com.dacdigitals.librarymanagementsystem.controller;

import com.dacdigitals.librarymanagementsystem.dto.ReservationDTO;
import com.dacdigitals.librarymanagementsystem.entity.Reservation;
import com.dacdigitals.librarymanagementsystem.service.ReservationService;
import com.dacdigitals.librarymanagementsystem.utils.CustomApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {

    public final ReservationService reservationService;

    @PostMapping("/reserve-a-book")
    public CustomApiResponse<Object> reserveBook(@RequestBody @Valid ReservationDTO reserve) {
        Reservation response = reservationService.makeReservation(reserve);

        return new CustomApiResponse<>("Book with id number " + reserve.getBookId() + " " +
                "has been successfully reserved!", response, HttpStatus.OK);
    }
}
