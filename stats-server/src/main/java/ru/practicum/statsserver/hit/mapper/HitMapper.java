package ru.practicum.statsserver.hit.mapper;

import ru.practicum.statsserver.hit.dto.HitDto;
import ru.practicum.statsserver.hit.model.Hit;

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