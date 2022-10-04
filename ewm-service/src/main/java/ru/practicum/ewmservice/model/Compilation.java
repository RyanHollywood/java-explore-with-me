package ru.practicum.ewmservice.model;

import lombok.Data;

import java.util.List;

@Data
public class Compilation {
    private List<Event> events;
    private long id;
    private boolean pinned;
    private String title;
}
