package ru.practicum.statsserver.event.service;

import org.springframework.http.ResponseEntity;
import ru.practicum.statsserver.event.dto.EventDto;

public interface EventService {
    ResponseEntity<String> create(EventDto eventDto);
}