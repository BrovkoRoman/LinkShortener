package org.example.advice;

import org.example.exception.BadRepositoryFunctionCallException;
import org.example.exception.IncorrectLongLinkException;
import org.example.exception.UnknownShortLinkException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class MyControllerAdvice {
    @ExceptionHandler(IncorrectLongLinkException.class)
    public ResponseEntity<String> handleIncorrectLongLink(IncorrectLongLinkException e) {
        return new ResponseEntity<String>("Incorrect long link", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnknownShortLinkException.class)
    public ResponseEntity<String> handleUnknownShortLink(UnknownShortLinkException e) {
        return new ResponseEntity<String>("There is no corresponding long link", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRepositoryFunctionCallException.class)
    public ResponseEntity<String> handleBadCall(BadRepositoryFunctionCallException e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<String> handleSQLException(SQLException e) {
        return new ResponseEntity<String>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
