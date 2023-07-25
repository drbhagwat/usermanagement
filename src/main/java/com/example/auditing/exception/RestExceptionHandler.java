package com.example.auditing.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
  @ExceptionHandler({ConstraintViolationException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ResponseEntity<Object> handleConstraintViolationException(
      ConstraintViolationException e) {
    return new ResponseEntity<>(e.getSQLException().getMessage(),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({ResourceNotFoundException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ResponseEntity<Object> handleResourceNotFoundException(
      ResourceNotFoundException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
  }
}