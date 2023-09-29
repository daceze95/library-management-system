package com.dacdigitals.librarymanagementsystem.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends Exception {
    @ExceptionHandler(NoBookFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> NoBookFoundException(NoBookFoundException ex) {
        Map<String, String> errorMsg = new HashMap<>();
        errorMsg.put("Error", ex.getMessage());
        return errorMsg;
    }

    @ExceptionHandler(NoPersonFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> NoPersonFoundException(NoPersonFoundException ex) {
        Map<String, String> errorMsg = new HashMap<>();
        errorMsg.put("Error", ex.getMessage());
        return errorMsg;
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> NoBookFoundException(MethodArgumentNotValidException ex) {
        Map<String, String> errorMsg = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err ->
                errorMsg.put(err.getField(), err.getDefaultMessage()));
        return errorMsg;
    }

    @ExceptionHandler({NoContentException.class})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Map<String, String> NoBookFoundException(NoContentException ex) {
        Map<String, String> Msg = new HashMap<>();
        Msg.put("message", ex.getMessage());
        return Msg;
    }

    @ExceptionHandler({NullPointerException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> NoBookFoundException(NullPointerException ex) {
        Map<String, String> Msg = new HashMap<>();
        Msg.put("Error", ex.getMessage());
        return Msg;
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> NoBookFoundException(HttpMessageNotReadableException ex) {
        Map<String, String> Msg = new HashMap<>();
        Msg.put("Error", ex.getMessage());
        return Msg;
    }

    @ExceptionHandler({ReservationNotFound.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> NoBookFoundException(ReservationNotFound ex) {
        Map<String, String> Msg = new HashMap<>();
        Msg.put("Error", ex.getMessage());
        return Msg;
    }

    @ExceptionHandler({BookNotAvailable.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> NoBookFoundException(BookNotAvailable ex) {
        Map<String, String> Msg = new HashMap<>();
        Msg.put("Error", ex.getMessage());
        return Msg;
    }
}
