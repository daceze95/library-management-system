package com.dacdigitals.librarymanagementsystem.exceptionHandler;

import org.springframework.http.HttpStatus;
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
}
