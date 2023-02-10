package com.kameleoon.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final RuntimeException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler({
            DataIntegrityViolationException.class,
            MethodArgumentNotValidException.class,
            InvalidParameterException.class,
            MissingServletRequestParameterException.class,
            MethodArgumentNotValidException.class, BadRequestException.class
    })

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleBadRequest(final Exception e) {
        return new ErrorMessage(
                e.getClass().getSimpleName(),
                e.getMessage(),
                "",
                "BAD_REQUEST",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
    }

    @ExceptionHandler({ConflictException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDoubleData(final Exception e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler({AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleForbidden(final Exception e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleThrowable(final Throwable e) {
        return new ErrorResponse(e.getMessage());
    }
}
