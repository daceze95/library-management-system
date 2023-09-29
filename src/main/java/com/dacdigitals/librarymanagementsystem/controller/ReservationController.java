package com.dacdigitals.librarymanagementsystem.controller;

import com.dacdigitals.librarymanagementsystem.dto.ReservationDTO;
import com.dacdigitals.librarymanagementsystem.entity.Reservation;
import com.dacdigitals.librarymanagementsystem.service.ReservationService;
import com.dacdigitals.librarymanagementsystem.utils.CustomApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/{userId}")
    public CustomApiResponse<Object> reserveBook(@PathVariable  Long userId) {
        List<Reservation> response =
                reservationService.getReservationByUserId(userId);

        return new CustomApiResponse<>("retrieved successfully!", response,
                HttpStatus.OK);
    }
    @GetMapping
    public CustomApiResponse<Object> getAllReservation() {
        List<Reservation> response = reservationService.getAllReservation();

        return new CustomApiResponse<>("retrieved successfully!", response,
                HttpStatus.OK);
    }
}
