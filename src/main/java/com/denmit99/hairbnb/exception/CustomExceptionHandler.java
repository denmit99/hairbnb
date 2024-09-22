package com.denmit99.hairbnb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<Object> accessDeniedException(AccessDeniedException ex) {
        ErrorMessage error = new ErrorMessage("access.denied", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> validationException(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .toList().toString();
        ErrorMessage error = new ErrorMessage("invalid.request", errors);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    //TODO add functionality to use custom http code in constraint validation annotations (e.g. @ListingIdExists)
    @ExceptionHandler(value = {HandlerMethodValidationException.class})
    public ResponseEntity<Object> methodValidationException(HandlerMethodValidationException ex) {
        var msg = ex.getAllValidationResults().getFirst().getResolvableErrors().getFirst().getDefaultMessage();
        ErrorMessage error = new ErrorMessage("invalid.request", msg);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> notFoundException(NotFoundException ex) {
        ErrorMessage error = new ErrorMessage("not.found", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<Object> defaultException(RuntimeException ex) {
        ErrorMessage error = new ErrorMessage("internal.error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
