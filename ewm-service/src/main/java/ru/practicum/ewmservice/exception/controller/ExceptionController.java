package ru.practicum.ewmservice.exception.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.ewmservice.exception.model.BadRequest;
import ru.practicum.ewmservice.exception.model.NotFound;

@RestControllerAdvice
public class ExceptionController {

    private final String pattern;

    public ExceptionController(@Value("${date.time.pattern}") String pattern) {
        this.pattern = pattern;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.OK)
    public String handleException(final NotFound exception) {
        /*
        return ApiErrorDto.builder()
                .status("NOT_FOUND")
                .reason("The required object was not found.")
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern)))
                .build();
         */
        return "null";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.OK)
    public String handleException(final BadRequest exception) {
        /*
        return ApiErrorDto.builder()
                .status("FORBIDDEN")
                .reason("For the requested operation the conditions are not met.")
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern)))
                .build();
         */
        return "null";
    }
}