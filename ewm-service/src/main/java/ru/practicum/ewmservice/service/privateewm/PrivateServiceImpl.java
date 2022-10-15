package ru.practicum.ewmservice.service.privateewm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.dto.event.EventFullDto;
import ru.practicum.ewmservice.dto.event.EventShortDto;
import ru.practicum.ewmservice.dto.event.NewEventDto;
import ru.practicum.ewmservice.dto.event.UpdateEventRequest;
import ru.practicum.ewmservice.dto.request.ParticipationRequestDto;
import ru.practicum.ewmservice.exception.model.NotFound;
import ru.practicum.ewmservice.mapper.EventMapper;
import ru.practicum.ewmservice.mapper.ParticipationRequestMapper;
import ru.practicum.ewmservice.model.*;
import ru.practicum.ewmservice.storage.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PrivateServiceImpl implements PrivateService {

    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ParticipationRequestRepository requestRepository;
    private final LocationRepository locationRepository;
    private final ParticipationRequestStatus defaultStatus = ParticipationRequestStatus.PENDING;
    private final String pattern;

    public PrivateServiceImpl(EventRepository eventRepository, CategoryRepository categoryRepository, UserRepository userRepository,
                              ParticipationRequestRepository requestRepository, LocationRepository locationRepository, @Value("${date.time.pattern}") String pattern) {
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.requestRepository = requestRepository;
        this.locationRepository = locationRepository;
        this.pattern = pattern;
    }

    @Override
    public List<EventShortDto> getEvents(long userId, int from, int size) {
        List<Event> events = eventRepository.findEventsByInitiatorId(userId, PageRequest.of(from / size, size));
        log.debug("");
        return toEventShortDtos(events);
    }

    @Override
    public EventFullDto updateEvent(long userId, UpdateEventRequest eventRequest) {
        Event eventToUpdate = eventRepository.findById(eventRequest.getEventId()).orElseThrow();
        eventToUpdate = updateEvent(eventToUpdate, eventRequest);
        eventToUpdate = eventRepository.save(eventToUpdate);
        log.debug("");
        return EventMapper.toEventFullDto(eventToUpdate, pattern);
    }

    @Override
    public EventFullDto postEvent(long userId, NewEventDto newEventDto) {
        Event newEvent = EventMapper.fromNewEventDto(newEventDto, pattern);
        newEvent.setInitiator(getUser(userId));
        newEvent.setState(EventState.PENDING);
        Location newEventLocation = locationRepository.save(newEvent.getLocation());
        Category newEventCategory = getCategory(newEventDto.getCategory());
        newEvent.setLocation(newEventLocation);
        newEvent.setCategory(newEventCategory);
        newEvent.setCreatedOn(LocalDateTime.now());
        newEvent = eventRepository.save(newEvent);
        log.debug("");
        return EventMapper.toEventFullDto(newEvent, pattern);
    }

    @Override
    public EventFullDto getUserEvent(long userId, long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        log.debug("");
        return EventMapper.toEventFullDto(event, pattern);
    }

    @Override
    public EventFullDto cancelUserEvent(long userId, long eventId) {
        Event eventToCancel = eventRepository.findById(userId).orElseThrow();
        eventToCancel.setState(EventState.CANCELED);
        eventToCancel = eventRepository.save(eventToCancel);
        log.debug("");
        return EventMapper.toEventFullDto(eventToCancel, pattern);
    }

    @Override
    public List<ParticipationRequestDto> getEventRequests(long userId, long eventId) {
        List<ParticipationRequest> requests = requestRepository.findAllByEvent(eventId);
        log.debug("");
        return toParticipationRequestDtos(requests);
    }

    @Override
    public ParticipationRequestDto confirmEventRequest(long userId, long eventId, long reqId) {
        ParticipationRequest requestToConfirm = requestRepository.findById(reqId).orElseThrow();
        requestToConfirm.setStatus(ParticipationRequestStatus.CONFIRMED);
        requestToConfirm = requestRepository.save(requestToConfirm);
        log.debug("");
        return ParticipationRequestMapper.toParticipationRequestDto(requestToConfirm, pattern);
    }

    @Override
    public ParticipationRequestDto rejectEventRequest(long userId, long eventId, long reqId) {
        ParticipationRequest requestToReject = requestRepository.findById(reqId).orElseThrow();
        requestToReject.setStatus(ParticipationRequestStatus.REJECTED);
        requestToReject = requestRepository.save(requestToReject);
        log.debug("");
        return ParticipationRequestMapper.toParticipationRequestDto(requestToReject, pattern);
    }

    @Override
    public List<ParticipationRequestDto> getUserRequests(long userId) {
        List<ParticipationRequest> requests = requestRepository.findAllByRequester(userId);
        log.debug("");
        return toParticipationRequestDtos(requests);
    }

    @Override
    public ParticipationRequestDto postUserRequest(long userId, long eventId) {
        ParticipationRequest newRequest = ParticipationRequest.builder()
                .created(LocalDateTime.now())
                .event(eventRepository.findById(eventId).orElseThrow())
                .requester(userRepository.findById(userId).orElseThrow())
                .status(defaultStatus)
                .build();
        newRequest = requestRepository.save(newRequest);
        log.debug("");
        return ParticipationRequestMapper.toParticipationRequestDto(newRequest, pattern);
    }

    @Override
    public ParticipationRequestDto cancelUserRequest(long userId, long reqId) {
        ParticipationRequest requestToCancel = requestRepository.findById(reqId).orElseThrow();
        requestToCancel.setStatus(ParticipationRequestStatus.REJECTED);
        requestToCancel = requestRepository.save(requestToCancel);
        log.debug("");
        return ParticipationRequestMapper.toParticipationRequestDto(requestToCancel, pattern);
    }

    private List<EventShortDto> toEventShortDtos(List<Event> events) {
        return events.stream()
                .map(event -> EventMapper.toEventShortDto(event, pattern))
                .collect(Collectors.toList());
    }

    private List<ParticipationRequestDto> toParticipationRequestDtos(List<ParticipationRequest> requests) {
        return requests.stream()
                .map(request -> ParticipationRequestMapper.toParticipationRequestDto(request, pattern))
                .collect(Collectors.toList());
    }

    private Event updateEvent(Event event, UpdateEventRequest eventRequest) {
        event.setAnnotation(eventRequest.getAnnotation());
        event.setCategory(categoryRepository.findById(eventRequest.getEventId()).orElseThrow());
        event.setDescription(eventRequest.getDescription());
        event.setEventDate(LocalDateTime.parse(eventRequest.getEventDate(), DateTimeFormatter.ofPattern(pattern)));
        event.setPaid(eventRequest.isPaid());
        event.setParticipantLimit(eventRequest.getParticipantLimit());
        event.setTitle(eventRequest.getTitle());
        return event;
    }

    private User getUser(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> {
            log.warn("User with id={} was not found.", userId);
            throw new NotFound("User with id=" + userId + " was not found.");
        });
    }

    private Category getCategory(long catId) {
        return categoryRepository.findById(catId).orElseThrow(() -> {
            log.warn("Category with id={} was not found.", catId);
            throw new NotFound("Category with id=" + catId + " was not found.");
        });
    }
}