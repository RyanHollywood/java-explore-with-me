package ru.practicum.ewmservice.mapper;

import ru.practicum.ewmservice.dto.hit.HitDto;
import ru.practicum.ewmservice.model.Hit;

import java.time.format.DateTimeFormatter;

public class HitMapper {
    public static HitDto toHitDto(Hit hit, String pattern) {
        return HitDto.builder()
                .app(hit.getApp())
                .uri(hit.getUri())
                .ip(hit.getIp())
                .timestamp(hit.getTimestamp().format(DateTimeFormatter.ofPattern(pattern)))
                .build();
    }
}