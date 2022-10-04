package ru.practicum.ewmservice.service.publicewm;

import ru.practicum.ewmservice.dto.category.CategoryDto;
import ru.practicum.ewmservice.dto.compilation.CompilationDto;
import ru.practicum.ewmservice.dto.event.EventFullDto;
import ru.practicum.ewmservice.dto.event.EventShortDto;

import java.util.List;

public interface PublicService {
    List<EventShortDto> getEvents();

    EventFullDto getEvent(long id);

    List<CompilationDto> getCompilations();

    CompilationDto getCompilation(long compId);

    List<CategoryDto> getCategories();

    CategoryDto getCategory(long catId);
}
