package ru.practicum.statsserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class StatsDto implements Comparable<StatsDto> {
    private String app;
    private String uri;
    private long hits;

    @Override
    public int compareTo(StatsDto o) {
        return 0;
    }
}