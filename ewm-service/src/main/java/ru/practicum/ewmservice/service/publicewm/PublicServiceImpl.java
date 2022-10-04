package ru.practicum.ewmservice.service.publicewm;

import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.dto.category.CategoryDto;
import ru.practicum.ewmservice.dto.compilation.CompilationDto;
import ru.practicum.ewmservice.dto.event.EventFullDto;
import ru.practicum.ewmservice.dto.event.EventShortDto;

import java.util.List;

@Service
public class PublicServiceImpl implements PublicService {
    @Override
    public List<EventShortDto> getEvents() {
        return null;
    }

    @Override
    public EventFullDto getEvent(long id) {
        return null;
    }

    @Override
    public List<CompilationDto> getCompilations() {
        return null;
    }

    @Override
    public CompilationDto getCompilation(long compId) {
        return null;
    }

    @Override
    public List<CategoryDto> getCategories() {
        return null;
    }

    @Override
    public CategoryDto getCategory(long catId) {
        return null;
    }
}