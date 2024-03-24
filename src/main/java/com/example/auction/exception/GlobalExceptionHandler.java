package com.example.auction.exception;

import com.example.auction.exception.err.PasswordsNotMatchException;
import com.example.auction.exception.err.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    public static final String
            DEFAULT_INTERNAL_SERVER_ERROR = ErrorMessages.DEFAULT_INTERNAL_SERVER_ERROR;

    @ExceptionHandler({Exception.class, Throwable.class})
    public ResponseEntity<Object> internalExceptionHandler(Exception e) {
        log.info(e.getLocalizedMessage(), e);
        return buildErrorResponse(
                Objects.nonNull(e.getLocalizedMessage())
                        ? e.getLocalizedMessage() :
                        DEFAULT_INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(PasswordsNotMatchException.class)
    public ResponseEntity<Object> passwordNotMatchExceptionHandler(Exception e) {
        log.info(e.getLocalizedMessage(), e);

        return buildErrorResponse(
                Objects.nonNull(e.getLocalizedMessage())
                        ? e.getLocalizedMessage() :
                        ErrorMessages.PASSWORD_NOT_MATCH, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> userNotFoundExceptionHandler(Exception e) {
        log.info(e.getLocalizedMessage(), e);

        return buildErrorResponse(
                Objects.nonNull(e.getLocalizedMessage())
                        ? e.getLocalizedMessage() :
                        ErrorMessages.USER_NOT_FOUND, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> buildErrorResponse(String message, HttpStatus status) {
        return ResponseEntity.status(status).body(new ExceptionResponse(message, status));
    }
}
