package ru.practicum.ewmservice.dto.event;

import lombok.Builder;
import lombok.Data;
import ru.practicum.ewmservice.dto.category.CategoryDto;
import ru.practicum.ewmservice.dto.location.LocationDto;
import ru.practicum.ewmservice.dto.user.UserShortDto;

@Data
@Builder
public class EventFullDto {
    private String annotation;
    private CategoryDto category;
    private long confirmedRequests;
    private String createOn;
    private String description;
    private String eventDate;
    private long id;
    private UserShortDto initiator;
    private LocationDto location;
    private boolean paid;
    private int participantLimit;
    private String publishedOn;
    private boolean requestModeration;
    private String state;
    private String title;
    private long views;
}