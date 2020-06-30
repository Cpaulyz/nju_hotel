package com.example.hotel.util;

import org.springframework.http.HttpStatus;

public class ServiceException extends Exception {
    private int error;

    public ServiceException() {
        this(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

    public ServiceException(int error) {
        this(error, message(error));
    }

    public ServiceException(String message) {
        this(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
    }

    public ServiceException(int error, String message) {
        super(message);
        this.error = error;
    }

    private static String message(int error) {
        try {
            return HttpStatus.valueOf(error).getReasonPhrase();
        } catch (IllegalArgumentException e) {
            return HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
        }
    }
}
