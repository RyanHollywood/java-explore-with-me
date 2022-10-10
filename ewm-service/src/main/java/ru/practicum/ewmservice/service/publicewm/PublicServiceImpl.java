package ru.practicum.ewmservice.service.publicewm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.client.StatsClient;
import ru.practicum.ewmservice.dto.category.CategoryDto;
import ru.practicum.ewmservice.dto.compilation.CompilationDto;
import ru.practicum.ewmservice.dto.event.EventFullDto;
import ru.practicum.ewmservice.dto.event.EventShortDto;
import ru.practicum.ewmservice.storage.CategoryRepository;
import ru.practicum.ewmservice.storage.CompilationRepository;
import ru.practicum.ewmservice.storage.UserRepository;

import java.util.List;

@Service
public class PublicServiceImpl implements PublicService {

    private final StatsClient statsClient;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final CompilationRepository compilationRepository;

    @Autowired
    public PublicServiceImpl(StatsClient statsClient, CategoryRepository categoryRepository, UserRepository userRepository, CompilationRepository compilationRepository) {
        this.statsClient = statsClient;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.compilationRepository = compilationRepository;
    }

    @Override
    public List<EventShortDto> getEvents(String text,
                                         List<Long> categories,
                                         boolean paid,
                                         String rangeStart,
                                         String rangeEnd,
                                         boolean onlyAvailable,
                                         String sort,
                                         int from,
                                         int size) {
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