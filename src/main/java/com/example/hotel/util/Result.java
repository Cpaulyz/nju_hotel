package com.example.hotel.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Result {
    public static ResponseEntity<?> ok(Object object) {
        return ResponseEntity.ok(object);
    }

    public static ResponseEntity<?> ok() {
        return ok(null);
    }

    public static ResponseEntity<?> error(HttpStatus status, String msg) {
        return ResponseEntity.status(status).body(msg);
    }

    public static ResponseEntity<?> error(HttpStatus status) {
        return error(status, null);
    }
}
