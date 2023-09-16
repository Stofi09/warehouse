package com.warehouse.warehouse.utility;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<String> handleDataAccessException(DataAccessException dae) {
        // You can log the exception here if needed
        return new ResponseEntity<>("Error occurred while interacting with the database.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
