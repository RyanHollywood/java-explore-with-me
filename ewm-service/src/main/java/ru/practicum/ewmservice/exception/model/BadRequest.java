package ru.practicum.ewmservice.exception.model;

public class BadRequest extends RuntimeException {
    public BadRequest(String msg) {
        super(msg);
    }
}
