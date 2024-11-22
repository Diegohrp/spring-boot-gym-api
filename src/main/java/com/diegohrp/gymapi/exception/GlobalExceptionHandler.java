package com.diegohrp.gymapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        List<Map<String, String>> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> {
                    Map<String, String> fieldError = new HashMap<>();
                    fieldError.put("field", error.getField());
                    fieldError.put("message", error.getDefaultMessage());
                    return fieldError;
                }).collect(Collectors.toList());
        Map<String, Object> response = new HashMap<>();
        response.put("type", "Bad Request");
        response.put("title", "Validation error");
        response.put("detail", "One or more fields don't follow the expected format");
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("errors", errors);


        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
