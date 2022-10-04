package ru.practicum.ewmservice.dto.event;

import lombok.Builder;
import lombok.Data;
import ru.practicum.ewmservice.dto.category.CategoryDto;
import ru.practicum.ewmservice.dto.location.LocationDto;

@Data
@Builder
public class NewEventDto {
    private String annotation;
    private CategoryDto category;
    private String description;
    private String eventDate;
    private LocationDto location;
    private boolean paid;
    private int participantLimit;
    private boolean requestModeration;
    private String title;
}