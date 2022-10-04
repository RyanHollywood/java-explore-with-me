package ru.practicum.ewmservice.mapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DateTimePattern {

    public static String pattern;

    @Value("${date.time.pattern}")
    public void setPattern(String pattern) {
        DateTimePattern.pattern = pattern;
    }
}