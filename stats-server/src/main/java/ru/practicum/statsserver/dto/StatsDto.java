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
        int result = 0;
        if (app.compareTo(o.getApp()) > 0) {
            result = 1;
        } else if (app.compareTo(o.getApp()) < 0) {
            result = -1;
        } else {
            if (uri.compareTo(o.getUri()) > 0) {
                result = 1;
            } else {
                result = -1;
            }
        }
        return result;
    }
}