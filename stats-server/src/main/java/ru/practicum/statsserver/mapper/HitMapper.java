package ru.practicum.statsserver.mapper;

import ru.practicum.statsserver.dto.HitDto;
import ru.practicum.statsserver.model.Hit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HitMapper {
    public static Hit fromDto(HitDto eventDto) {
        return  Hit.builder()
                .app(eventDto.getApp())
                .uri(eventDto.getUri())
                .ip(eventDto.getIp())
                .timestamp(LocalDateTime.parse(eventDto.getTimestamp(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }
}