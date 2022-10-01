package ru.practicum.statsserver.exception;

public class DatetimeParsingError extends RuntimeException {
    public DatetimeParsingError(String msg) {
        super(msg);
    }
}