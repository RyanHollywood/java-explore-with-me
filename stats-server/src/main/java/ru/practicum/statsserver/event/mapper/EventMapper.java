package ru.practicum.statsserver.event.mapper;

import ru.practicum.statsserver.event.dto.EventDto;
import ru.practicum.statsserver.event.model.Event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventMapper {
    public static Event fromDto(EventDto eventDto) {
        return  Event.builder()
                .app(eventDto.getApp())
                .uri(eventDto.getUri())
                .ip(eventDto.getIp())
                .timestamp(LocalDateTime.parse(eventDto.getTimestamp(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }
}