package ru.practicum.ewmservice.service.adminewm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import ru.practicum.ewmservice.mapper.CategoryMapper;
import ru.practicum.ewmservice.mapper.EventMapper;
import ru.practicum.ewmservice.mapper.UserMapper;
import ru.practicum.ewmservice.model.*;
import ru.practicum.ewmservice.storage.CategoryRepository;
import ru.practicum.ewmservice.storage.CompilationRepository;
import ru.practicum.ewmservice.storage.EventRepository;
import ru.practicum.ewmservice.storage.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final CompilationRepository compilationRepository;

    @Autowired
    public AdminServiceImpl(EventRepository eventRepository, CategoryRepository categoryRepository, UserRepository userRepository,
                            CompilationRepository compilationRepository) {
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.compilationRepository = compilationRepository;
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
        log.debug("");
        return null;
    }

    @Override
    public EventFullDto publishEvent(long eventId) {
        Event eventToPublish = eventRepository.findById(eventId).orElseThrow();
        eventToPublish.setState(EventState.PUBLISHED);
        eventRepository.save(eventToPublish);
        log.debug("");
        return EventMapper.toEventFullDto(eventToPublish);
    }

    @Override
    public EventFullDto rejectEvent(long eventId) {
        Event eventToReject = eventRepository.findById(eventId).orElseThrow();
        eventToReject.setState(EventState.CANCELED);
        log.debug("");
        return EventMapper.toEventFullDto(eventToReject);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto CategoryDto) {
        log.debug("");
        return null;
    }

    @Override
    public CategoryDto createCategory(NewCategoryDto newCategoryDto) {
        Category newCategory = CategoryMapper.fromNewCategoryDto(newCategoryDto);
        newCategory = categoryRepository.save(newCategory);
        log.debug("New category {} with id:{} created", newCategory.getName(), newCategory.getId());
        return CategoryMapper.toCategoryDto(newCategory);
    }

    @Override
    public void deleteCategory(long catId) {
        categoryRepository.deleteById(catId);
        log.debug("");
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
        log.debug("New user {} created", newUser.getName());
        return UserMapper.toUserDto(newUser);
    }

    @Override
    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
        log.debug("User id:{} deleted", userId);
    }

    @Override
    public CompilationDto createCompilation(NewCompilationDto compilationDto) {
        log.debug("");
        return null;
    }

    @Override
    public void deleteCompilation(long compId) {
        compilationRepository.deleteById(compId);
        log.debug("Compilation id:{} deleted", compId);
    }

    @Override
    public void deleteEventFromCompilation(long compId, long eventId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow();
        List<Event> events = compilation.getEvents();
        log.debug("");
    }

    @Override
    public void addEventToCompilation(long compId, long eventId) {
        Event eventToAdd = eventRepository.findById(eventId).orElseThrow();
        Compilation compilation = compilationRepository.findById(compId).orElseThrow();
        List<Event> events = compilation.getEvents();
        events.add(eventToAdd);
        compilation.setEvents(events);
        compilationRepository.save(compilation);
        log.debug("");
    }

    @Override
    public void unpinCompilation(long compId) {
        Compilation compToUnpin = compilationRepository.findById(compId).orElseThrow();
        compToUnpin.setPinned(false);
        compilationRepository.save(compToUnpin);
        log.debug("");
    }

    @Override
    public void pinCompilation(long compId) {
        Compilation compToPin = compilationRepository.findById(compId).orElseThrow();
        compToPin.setPinned(true);
        compilationRepository.save(compToPin);
        log.debug("");
    }

    private List<EventFullDto> toEventFullDtos(List<Event> events) {
        return events.stream()
                .map(EventMapper::toEventFullDto)
                .collect(Collectors.toList());
    }

    private List<UserDto> toUserDtos(List<User> users) {
        return users.stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }
}
