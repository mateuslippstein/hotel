package com.ml.hotel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicatedDocumentException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatedDocumentException(DuplicatedDocumentException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Duplicated Document", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}
