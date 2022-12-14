package ru.practicum.ewmservice.dto.compilation;

import lombok.Builder;
import lombok.Data;
import ru.practicum.ewmservice.dto.event.EventShortDto;

import java.util.List;

@Data
@Builder
public class CompilationDto {
    private List<EventShortDto> events;
    private long id;
    private boolean pinned;
    private String title;
}
