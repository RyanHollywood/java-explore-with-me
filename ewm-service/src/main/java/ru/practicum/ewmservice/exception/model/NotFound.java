package ru.practicum.ewmservice.exception.model;

public class NotFound extends RuntimeException {
    public NotFound(String msg) {
        super(msg);
    }
}
