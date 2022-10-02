package ru.practicum.statsserver.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatsDto {
    private String app;
    private String uri;
    private long hits;
}