package ru.practicum.statsserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Stats {
    private String app;
    private String uri;
    private long hits;
}
