package com.ecom.task.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(OverLimitException.class)
    public ResponseEntity<String> handleOverLimitException(OverLimitException ex) {
        return new ResponseEntity<>("Rate limit exceeded: " + ex.getMessage(), HttpStatus.BAD_GATEWAY);
    }
}
