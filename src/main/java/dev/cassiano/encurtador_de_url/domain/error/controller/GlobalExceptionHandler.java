package dev.cassiano.encurtador_de_url.domain.error.controller;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import dev.cassiano.encurtador_de_url.domain.error.exceptions.ForbitenException;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> NullPointerExceptionHandler(NullPointerException exception) {
        return ResponseEntity
                .badRequest()
                .body(exception.getMessage());
    }   

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> NotFoundExceptionHandler(NotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());        
    }

    @ExceptionHandler(ForbitenException.class)
    public ResponseEntity<String> ForbitenExceptionHandler(NotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(exception.getMessage());        
    }
}
