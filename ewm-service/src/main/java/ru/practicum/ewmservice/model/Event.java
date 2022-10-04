package ru.practicum.ewmservice.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Event {
    private String annotation;
    private Category category;
    private long confirmedRequests;
    private LocalDateTime createOn;
    private String description;
    private LocalDateTime eventDate;
    private long id;
    private User initiator;
    private Location location;
    private boolean paid;
    private int participantLimit;
    private String publishedOn;
    private boolean requestModeration;
    private String state;
    private String title;
    private long views;
}
