package ru.practicum.ewmservice.dto.event;

import lombok.Data;
import ru.practicum.ewmservice.dto.location.LocationDto;

@Data
public class AdminUpdateEventRequestDto {
    private String annotation;
    private long category;
    private String description;
    private String eventDate;
    private LocationDto location;
    private boolean paid;
    private int participantLimit;
    private boolean requestModeration;
    private String title;
}
