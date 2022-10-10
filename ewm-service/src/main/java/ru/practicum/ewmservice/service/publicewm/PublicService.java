package ru.practicum.ewmservice.service.publicewm;

import ru.practicum.ewmservice.dto.category.CategoryDto;
import ru.practicum.ewmservice.dto.compilation.CompilationDto;
import ru.practicum.ewmservice.dto.event.EventFullDto;
import ru.practicum.ewmservice.dto.event.EventShortDto;

import java.util.List;

public interface PublicService {
    List<EventShortDto> getEvents(String text, List<Long> categories, boolean paid, String rangeStart, String rangeEnd,
                                  boolean onlyAvailable, String sort, int from, int size);

    EventFullDto getEvent(long id);

    List<CompilationDto> getCompilations(boolean pinned, int from, int size);

    CompilationDto getCompilation(long compId);

    List<CategoryDto> getCategories(int from, int size);

    CategoryDto getCategory(long catId);
}
