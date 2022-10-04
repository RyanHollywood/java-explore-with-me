package ru.practicum.statsserver.mapper;

import org.springframework.beans.factory.annotation.Value;
import ru.practicum.statsserver.dto.HitDto;
import ru.practicum.statsserver.model.Hit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HitMapper {
    @Value("${date.time.pattern}")
    private static String pattern;

    public static Hit fromDto(HitDto hitDto) {
        return  Hit.builder()
                .app(hitDto.getApp())
                .uri(hitDto.getUri())
                .ip(hitDto.getIp())
                .timestamp(LocalDateTime.parse(hitDto.getTimestamp(), DateTimeFormatter.ofPattern(pattern)))
                .build();
    }
}