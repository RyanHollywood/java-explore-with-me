package ru.practicum.ewmservice.dto.compilation;

import lombok.Data;

import java.util.List;

@Data
public class NewCompilationDto {
    private List<Long> events;
    private boolean pinned;
    private String title;
}
