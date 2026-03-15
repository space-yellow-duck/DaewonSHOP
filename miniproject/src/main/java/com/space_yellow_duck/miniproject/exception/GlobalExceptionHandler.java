package com.space_yellow_duck.miniproject.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException e) {

        Map<String, String> body = new HashMap<>();
        body.put("message", e.getMessage());
        
        return ResponseEntity.badRequest().body(body); // HTTP 400
    }
}
