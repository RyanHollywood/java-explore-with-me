package ru.practicum.ewmservice.service.adminewm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.dto.category.CategoryDto;
import ru.practicum.ewmservice.dto.category.NewCategoryDto;
import ru.practicum.ewmservice.dto.compilation.CompilationDto;
import ru.practicum.ewmservice.dto.compilation.NewCompilationDto;
import ru.practicum.ewmservice.dto.event.AdminUpdateEventRequestDto;
import ru.practicum.ewmservice.dto.event.EventFullDto;
import ru.practicum.ewmservice.dto.user.NewUserRequest;
import ru.practicum.ewmservice.dto.user.UserDto;
import ru.practicum.ewmservice.exception.model.NotFound;
import ru.practicum.ewmservice.mapper.*;
import ru.practicum.ewmservice.model.*;
import ru.practicum.ewmservice.storage.CategoryRepository;
import ru.practicum.ewmservice.storage.CompilationRepository;
import ru.practicum.ewmservice.storage.EventRepository;
import ru.practicum.ewmservice.storage.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final CompilationRepository compilationRepository;
    private final String pattern;

    @Autowired
    public AdminServiceImpl(EventRepository eventRepository, CategoryRepository categoryRepository, UserRepository userRepository,
                            CompilationRepository compilationRepository, @Value("${date.time.pattern}") String pattern) {
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.compilationRepository = compilationRepository;
        this.pattern = pattern;
    }

    @Override
    public List<EventFullDto> getEvents(List<Long> users, List<String> states, List<Long> categories, String rangeStart,
                                        String rangeEnd, int from, int size) {
        List<Event> events = eventRepository.getEventsAdmin(users, states, categories, rangeStart, rangeEnd, PageRequest.of(from / size, size));
        log.debug("");
        return toEventFullDtos(events);
    }

    @Override
    public EventFullDto editEvent(long eventId, AdminUpdateEventRequestDto requestDto) {
        Event eventToEdit = getEvent(eventId);
        eventToEdit = updateEvent(eventToEdit, requestDto);
        eventToEdit = eventRepository.save(eventToEdit);
        log.debug("Event with id={} was edited.", eventId);
        return EventMapper.toEventFullDto(eventToEdit, pattern);
    }

    @Override
    public EventFullDto publishEvent(long eventId) {
        Event eventToPublish = getEvent(eventId);
        eventToPublish.setState(EventState.PUBLISHED);
        eventRepository.save(eventToPublish);
        log.debug("Event with id={} was published.", eventId);
        return EventMapper.toEventFullDto(eventToPublish, pattern);
    }

    @Override
    public EventFullDto rejectEvent(long eventId) {
        Event eventToReject = getEvent(eventId);
        eventToReject.setState(EventState.CANCELED);
        log.debug("Event with id={} was rejected.", eventId);
        return EventMapper.toEventFullDto(eventToReject, pattern);
    }

    @Override
    public CategoryDto getCategoryById(long catId) {
        Category category = getCategory(catId);
        log.debug("Category with id={} was found.", catId);
        return CategoryMapper.toCategoryDto(category);
    }


    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        Category categoryToUpdate = getCategory(categoryDto.getId());
        categoryToUpdate = updateCategory(categoryToUpdate, categoryDto);
        categoryToUpdate = categoryRepository.save(categoryToUpdate);
        log.debug("Category with id={} was updated.", categoryToUpdate.getId());
        return CategoryMapper.toCategoryDto(categoryToUpdate);
    }

    @Override
    public CategoryDto createCategory(NewCategoryDto newCategoryDto) {
        Category newCategory = CategoryMapper.fromNewCategoryDto(newCategoryDto);
        newCategory = categoryRepository.save(newCategory);
        log.debug("New category {} with id={} created.", newCategory.getName(), newCategory.getId());
        return CategoryMapper.toCategoryDto(newCategory);
    }

    @Override
    public void deleteCategory(long catId) {
        getCategory(catId);
        categoryRepository.deleteById(catId);
        log.debug("Category with id={} was deleted.", catId);
    }

    @Override
    public List<UserDto> getUsers(List<Long> ids, int from, int size) {
        List<User> users = userRepository.findByIdIn(ids, PageRequest.of(from / size, size));
        log.debug("");
        return toUserDtos(users);
    }

    @Override
    public UserDto createUser(NewUserRequest userRequest) {
        User newUser = UserMapper.fromNewUserRequest(userRequest);
        newUser = userRepository.save(newUser);
        log.debug("New user {} with id={} created.", newUser.getName(), newUser.getId());
        return UserMapper.toUserDto(newUser);
    }

    @Override
    public void deleteUser(long userId) {
        getUser(userId);
        userRepository.deleteById(userId);
        log.debug("User id={} deleted.", userId);
    }

    @Override
    public CompilationDto createCompilation(NewCompilationDto newCompilationDto) {
        Compilation newCompilation = CompilationMapper.toCompilation(newCompilationDto);
        newCompilation.setEvents(getEventsByIds(newCompilationDto.getEvents()));
        newCompilation = compilationRepository.save(newCompilation);
        log.debug("");
        return CompilationMapper.toCompilationDto(newCompilation, pattern);
    }

    @Override
    public void deleteCompilation(long compId) {
        getCompilation(compId);
        compilationRepository.deleteById(compId);
        log.debug("Compilation id:{} deleted", compId);
    }

    @Override
    public void deleteEventFromCompilation(long compId, long eventId) {
        Compilation compilation = getCompilation(compId);
        List<Event> events = compilation.getEvents();
        events = events.stream()
                .filter(event -> event.getId() != eventId)
                .collect(Collectors.toList());
        compilation.setEvents(events);
        compilationRepository.save(compilation);
        log.debug("");
    }

    @Override
    public void addEventToCompilation(long compId, long eventId) {
        Event eventToAdd = getEvent(eventId);
        Compilation compilation = getCompilation(compId);
        List<Event> events = compilation.getEvents();
        events.add(eventToAdd);
        compilation.setEvents(events);
        compilationRepository.save(compilation);
        log.debug("");
    }

    @Override
    public void unpinCompilation(long compId) {
        Compilation compToUnpin = getCompilation(compId);
        compToUnpin.setPinned(false);
        compilationRepository.save(compToUnpin);
        log.debug("");
    }

    @Override
    public void pinCompilation(long compId) {
        Compilation compToPin = getCompilation(compId);
        compToPin.setPinned(true);
        compilationRepository.save(compToPin);
        log.debug("");
    }

    private List<EventFullDto> toEventFullDtos(List<Event> events) {
        return events.stream()
                .map(event -> EventMapper.toEventFullDto(event, pattern))
                .collect(Collectors.toList());
    }

    private List<UserDto> toUserDtos(List<User> users) {
        return users.stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    private List<Event> getEventsByIds(List<Long> eventIds) {
        return eventRepository.findEventsByIdIn(eventIds);
    }

    private Event updateEvent(Event event, AdminUpdateEventRequestDto eventRequestDto) {
        event.setAnnotation(eventRequestDto.getAnnotation());
        event.setCategory(getCategory(eventRequestDto.getCategory()));
        event.setDescription(eventRequestDto.getDescription());
        event.setEventDate(LocalDateTime.parse(eventRequestDto.getEventDate(),
                DateTimeFormatter.ofPattern(pattern)));
        event.setPaid(eventRequestDto.isPaid());
        event.setParticipantLimit(eventRequestDto.getParticipantLimit());
        event.setRequestModeration(eventRequestDto.isRequestModeration());
        event.setTitle(eventRequestDto.getTitle());
        return event;
    }

    private Category updateCategory(Category category, CategoryDto categoryDto) {
        category.setName(categoryDto.getName());
        return category;
    }

    private Event getEvent(long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> {
            log.warn("Event with id={} was not found.", eventId);
            throw new NotFound("Event with id=" + eventId + " was not found.");
        });
    }

    private Category getCategory(long catId) {
        return categoryRepository.findById(catId).orElseThrow(() -> {
            log.warn("Category with id={} was not found.", catId);
            throw new NotFound("Category with id=" + catId + " was not found.");
        });
    }

    private Compilation getCompilation(long compId) {
        return compilationRepository.findById(compId).orElseThrow(() -> {
            log.warn("Compilation with id={} was not found.", compId);
            throw new NotFound("Compilation with id=" + compId + " was not found.");
        });
    }

    private User getUser(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> {
            log.warn("User with id={} was not found.", userId);
            throw new NotFound("User with id=" + userId + " was not found.");
        });
    }
}