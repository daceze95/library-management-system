package com.dacdigitals.librarymanagementsystem.utils;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CustomApiResponse<Object> {
    private String message;
    private Object data;
    private HttpStatus http;


    public CustomApiResponse(String message, Object data, HttpStatus http) {
        this.message = message;
        this.data = data;
        this.http = http;
    }

    public CustomApiResponse() {
    }
}
