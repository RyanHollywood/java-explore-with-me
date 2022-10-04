package ru.practicum.ewmservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Event {
    private Category category;
    private long confirmedRequests;
    private String createOn;
    private String description;
    private String eventDate;
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
