package com.denmit99.hairbnb.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

//    @ExceptionHandler(RuntimeException.class)
//    ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {
//        var response = "";
//        return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.CONFLICT, request);
//    }
//
//    @ExceptionHandler(ConstraintViolationException.class)
//    ResponseEntity<Object> handleConsException(ConstraintViolationException ex, WebRequest request) {
//        var response = "";
//        return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.CONFLICT, request);
//    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Object> resourceNotFoundException(RuntimeException ex, WebRequest request) {
        return new ResponseEntity<Object>("sd", HttpStatus.NOT_FOUND);
    }
}
