package ru.practicum.ewmservice.dto.event;

import lombok.Data;
import ru.practicum.ewmservice.dto.category.CategoryDto;
import ru.practicum.ewmservice.dto.user.UserShortDto;

@Data
public class EventShortDto {
    private String annotation;
    private CategoryDto category;
    private long confirmedRequests;
    private String eventDate;
    private long id;
    private UserShortDto initiator;
    private boolean paid;
    private String title;
    private long views;
}
