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

    @GetMapping("/user")
    public CustomApiResponse<Object> getReservationByUserId(@RequestParam(
            "user_id") Long userId) {
        Reservation response =
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

    @GetMapping("/reservation")
    public CustomApiResponse<Object> getReservationByRservId(@RequestParam(
            "reservation_id") Long reservationId) {
        Reservation response =
                reservationService.getReservationByRservId(reservationId);

        return new CustomApiResponse<>("retrieved successfully!", response,
                HttpStatus.OK);
    }

    @GetMapping("/book")
    public CustomApiResponse<Object> getReservationByBookId(@RequestParam(
            "book_id") Long bookId) {
        Reservation response =
                reservationService.getReservationByBookId(bookId);

        return new CustomApiResponse<>("retrieved successfully!", response,
                HttpStatus.OK);
    }

    @PostMapping("/cancel/{id}")
    public CustomApiResponse<Object> cancelReservation(@PathVariable Long id) {
        String res = reservationService.cancelReservation(id);
        return new CustomApiResponse<>("cancelled", res, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public CustomApiResponse<Object> deleteReservation(@PathVariable Long id) {
        String res = reservationService.deleteReservation(id);
        return new CustomApiResponse<>("deleted!", res, HttpStatus.OK);
    }

}
