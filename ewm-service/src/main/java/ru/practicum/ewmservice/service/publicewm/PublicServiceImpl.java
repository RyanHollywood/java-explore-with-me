package ru.practicum.ewmservice.service.publicewm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.client.StatsClient;
import ru.practicum.ewmservice.dto.category.CategoryDto;
import ru.practicum.ewmservice.dto.compilation.CompilationDto;
import ru.practicum.ewmservice.dto.event.EventFullDto;
import ru.practicum.ewmservice.dto.event.EventShortDto;
import ru.practicum.ewmservice.mapper.CategoryMapper;
import ru.practicum.ewmservice.mapper.CompilationMapper;
import ru.practicum.ewmservice.mapper.EventMapper;
import ru.practicum.ewmservice.model.Category;
import ru.practicum.ewmservice.model.Compilation;
import ru.practicum.ewmservice.model.Event;
import ru.practicum.ewmservice.storage.CategoryRepository;
import ru.practicum.ewmservice.storage.CompilationRepository;
import ru.practicum.ewmservice.storage.EventRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PublicServiceImpl implements PublicService {

    private final StatsClient statsClient;
    private final CategoryRepository categoryRepository;
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Autowired
    public PublicServiceImpl(StatsClient statsClient, CategoryRepository categoryRepository, CompilationRepository compilationRepository, EventRepository eventRepository) {
        this.statsClient = statsClient;
        this.categoryRepository = categoryRepository;
        this.compilationRepository = compilationRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public List<EventShortDto> getEvents(String text, List<Long> categories, boolean paid, String rangeStart,
                                         String rangeEnd, boolean onlyAvailable, String sort, int from, int size) {
        List<Event> events;
        if (onlyAvailable) {
            events = eventRepository.getEventsPublishedByTextCategoryDateSorted(text, categories, paid, rangeStart, rangeEnd,
                    sort.toLowerCase(), PageRequest.of(from / size, size));
        } else {
            events = eventRepository.getEventsByTextCategoryDateSorted(text, categories, paid, rangeStart, rangeEnd,
                    sort.toLowerCase(), PageRequest.of(from / size, size));
        }
        log.debug("");
        return toShortDtosList(events);
    }

    @Override
    public EventFullDto getEvent(long id) {
        Event event = eventRepository.findById(id).orElseThrow();
        log.debug("");
        return EventMapper.toEventFullDto(event);
    }

    @Override
    public List<CompilationDto> getCompilations(boolean pinned, int from, int size) {
        Page<Compilation> compilations = compilationRepository.findCompilationByPinned(pinned, PageRequest.of(from / size, size));
        log.debug("");
        return toCompilationDto(compilations);
    }

    @Override
    public CompilationDto getCompilation(long compId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow();
        log.debug("");
        return CompilationMapper.toCompilationDto(compilation);
    }

    @Override
    public List<CategoryDto> getCategories(int from, int size) {
        Page<Category> categories = categoryRepository.findAll(PageRequest.of(from / size, size));
        log.debug("");
        return toCategoryDtos(categories);
    }

    @Override
    public CategoryDto getCategory(long catId) {
        Category category = categoryRepository.findById(catId).orElseThrow();
        log.debug("");
        return CategoryMapper.toCategoryDto(category);
    }

    private List<EventShortDto> toShortDtosList(List<Event> events) {
        return events.stream().map(EventMapper::toEventShortDto).collect(Collectors.toList());
    }

    private List<CompilationDto> toCompilationDto(Page<Compilation> compilations) {
        return compilations.stream()
                .map(CompilationMapper::toCompilationDto)
                .collect(Collectors.toList());
    }

    private List<CategoryDto> toCategoryDtos(Page<Category> categories) {
        return categories.stream()
                .map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }
}