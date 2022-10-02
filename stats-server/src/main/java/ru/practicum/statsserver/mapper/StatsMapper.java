package ru.practicum.statsserver.mapper;

import ru.practicum.statsserver.dto.StatsDto;
import ru.practicum.statsserver.model.Stats;

public class StatsMapper {
    public static Stats fromObject(Object obj) {
        return (Stats) obj;
    }

    public static StatsDto toDto(Stats stats) {
        return StatsDto.builder()
                .app(stats.getApp())
                .uri(stats.getUri())
                .hits(stats.getHits())
                .build();
    }
}
