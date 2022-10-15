package ru.practicum.ewmservice.service.publicewm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.client.StatsClient;
import ru.practicum.ewmservice.dto.category.CategoryDto;
import ru.practicum.ewmservice.dto.compilation.CompilationDto;
import ru.practicum.ewmservice.dto.event.EventFullDto;
import ru.practicum.ewmservice.dto.event.EventShortDto;
import ru.practicum.ewmservice.exception.model.NotFound;
import ru.practicum.ewmservice.mapper.CategoryMapper;
import ru.practicum.ewmservice.mapper.CompilationMapper;
import ru.practicum.ewmservice.mapper.EventMapper;
import ru.practicum.ewmservice.model.Category;
import ru.practicum.ewmservice.model.Compilation;
import ru.practicum.ewmservice.model.Event;
import ru.practicum.ewmservice.storage.CategoryRepository;
import ru.practicum.ewmservice.storage.CompilationRepository;
import ru.practicum.ewmservice.storage.EventRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PublicServiceImpl implements PublicService {

    private final StatsClient statsClient;
    private final CategoryRepository categoryRepository;
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final String pattern;

    @Autowired
    public PublicServiceImpl(StatsClient statsClient, CategoryRepository categoryRepository, CompilationRepository compilationRepository,
                             EventRepository eventRepository, @Value("${date.time.pattern}") String pattern) {
        this.statsClient = statsClient;
        this.categoryRepository = categoryRepository;
        this.compilationRepository = compilationRepository;
        this.eventRepository = eventRepository;
        this.pattern = pattern;
    }

    @Override
    public List<EventShortDto> getEvents(String text, List<Long> categories, boolean paid, String rangeStart, String rangeEnd,
                                         boolean onlyAvailable, String sort, int from, int size, HttpServletRequest request) {
        List<Event> events = new ArrayList<>();
        if (onlyAvailable) {
            if (sort.equals("EVENT_DATE")) {
                events = eventRepository.getEventsPublicAvailableOrderByEventDate(text, categories, paid, rangeStart, rangeEnd,
                        PageRequest.of(from / size, size));
            } else if (sort.equals("VIEWS")) {
                events = eventRepository.getEventsPublicAvailableOrderByViews(text, categories, paid, rangeStart, rangeEnd,
                        PageRequest.of(from / size, size));
            }
            log.debug("");
        } else {
            if (sort.equals("EVENT_DATE")) {
                events = eventRepository.getEventsPublicAllOrderByEventDate(text, categories, paid, rangeStart, rangeEnd,
                        PageRequest.of(from / size, size));
            } else if(sort.equals("VIEWS")) {
                events = eventRepository.getEventsPublicAllOrderByViews(text, categories, paid, rangeStart, rangeEnd,
                        PageRequest.of(from / size, size));
            }
            log.debug("");
        }
        statsClient.sendHit(request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now());
        return toShortDtosList(events);
    }

    @Override
    public EventFullDto getEvent(long eventId, HttpServletRequest request) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> {
            log.warn("Event with id={} was not found.", eventId);
            throw new NotFound("Event with id=" + eventId + " was not found.");
        });
        log.debug("");
        statsClient.sendHit(request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now());
        return EventMapper.toEventFullDto(event, pattern);
    }

    @Override
    public List<CompilationDto> getCompilations(boolean pinned, int from, int size) {
        Page<Compilation> compilations = compilationRepository.findCompilationByPinned(pinned, PageRequest.of(from / size, size));
        log.debug("");
        return toCompilationDto(compilations);
    }

    @Override
    public CompilationDto getCompilation(long compId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(() -> {
            log.warn("Compilation with id={} was not found.", compId);
            throw new NotFound("Compilation with id=" + compId + " was not found.");
        });
        log.debug("");
        return CompilationMapper.toCompilationDto(compilation, pattern);
    }

    @Override
    public List<CategoryDto> getCategories(int from, int size) {
        Page<Category> categories = categoryRepository.findAll(PageRequest.of(from / size, size));
        log.debug("");
        return toCategoryDtos(categories);
    }

    @Override
    public CategoryDto getCategory(long catId) {
        Category category = categoryRepository.findById(catId).orElseThrow(() -> {
            log.warn("Category with id={} was not found.", catId);
            throw new NotFound("Category with id=" + catId + " was not found.");
        });
        log.debug("");
        return CategoryMapper.toCategoryDto(category);
    }

    private List<EventShortDto> toShortDtosList(List<Event> events) {
        return events.stream()
                .map(event -> EventMapper.toEventShortDto(event, pattern))
                .collect(Collectors.toList());
    }

    private List<CompilationDto> toCompilationDto(Page<Compilation> compilations) {
        return compilations.stream()
                .map(compilation -> CompilationMapper.toCompilationDto(compilation, pattern))
                .collect(Collectors.toList());
    }

    private List<CategoryDto> toCategoryDtos(Page<Category> categories) {
        return categories.stream()
                .map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }
}