package ru.practicum.statsserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Stats implements Comparable<Stats> {
    private String app;
    private String uri;
    private long hits;

    @Override
    public int compareTo(Stats o) {
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
