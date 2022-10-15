package ru.practicum.ewmservice.mapper;

import ru.practicum.ewmservice.dto.compilation.CompilationDto;
import ru.practicum.ewmservice.dto.compilation.NewCompilationDto;
import ru.practicum.ewmservice.dto.event.EventShortDto;
import ru.practicum.ewmservice.model.Compilation;
import ru.practicum.ewmservice.model.Event;

import java.util.List;
import java.util.stream.Collectors;

public class CompilationMapper {

    public static Compilation toCompilation(NewCompilationDto newCompilationDto) {
        return Compilation.builder()
                .pinned(newCompilationDto.isPinned())
                .title(newCompilationDto.getTitle())
                .build();
    }

    public static CompilationDto toCompilationDto(Compilation compilation, String pattern) {
        return CompilationDto.builder()
                .events(toEventShortDtos(compilation.getEvents(), pattern))
                .id(compilation.getId())
                .pinned(compilation.isPinned())
                .title(compilation.getTitle())
                .build();
    }

    private static List<EventShortDto> toEventShortDtos(List<Event> events, String pattern) {
        return events.stream()
                .map(event -> EventMapper.toEventShortDto(event, pattern))
                .collect(Collectors.toList());
    }
}
