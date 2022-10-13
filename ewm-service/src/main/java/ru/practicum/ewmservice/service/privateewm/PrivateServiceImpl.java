package ru.practicum.ewmservice.service.privateewm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.dto.event.EventFullDto;
import ru.practicum.ewmservice.dto.event.EventShortDto;
import ru.practicum.ewmservice.dto.event.NewEventDto;
import ru.practicum.ewmservice.dto.event.UpdateEventRequest;
import ru.practicum.ewmservice.dto.request.ParticipationRequestDto;
import ru.practicum.ewmservice.mapper.DateTimePattern;
import ru.practicum.ewmservice.mapper.EventMapper;
import ru.practicum.ewmservice.mapper.ParticipationRequestMapper;
import ru.practicum.ewmservice.model.Event;
import ru.practicum.ewmservice.model.EventState;
import ru.practicum.ewmservice.model.ParticipationRequest;
import ru.practicum.ewmservice.model.ParticipationRequestStatus;
import ru.practicum.ewmservice.storage.CategoryRepository;
import ru.practicum.ewmservice.storage.EventRepository;
import ru.practicum.ewmservice.storage.ParticipationRequestRepository;
import ru.practicum.ewmservice.storage.UserRepository;

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
    private final ParticipationRequestStatus defaultStatus = ParticipationRequestStatus.PENDING;

    public PrivateServiceImpl(EventRepository eventRepository, CategoryRepository categoryRepository, UserRepository userRepository,
                              ParticipationRequestRepository requestRepository) {
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.requestRepository = requestRepository;
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
        return EventMapper.toEventFullDto(eventToUpdate);
    }

    @Override
    public EventFullDto postEvent(long userId, NewEventDto newEventDto) {
        Event newEvent = EventMapper.fromNewEventDto(newEventDto);
        newEvent.setInitiator(userRepository.findById(userId).orElseThrow());
        newEvent.setState(EventState.PENDING);
        newEvent = eventRepository.save(newEvent);
        log.debug("");
        return EventMapper.toEventFullDto(newEvent);
    }

    @Override
    public EventFullDto getUserEvent(long userId, long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        log.debug("");
        return EventMapper.toEventFullDto(event);
    }

    @Override
    public EventFullDto cancelUserEvent(long userId, long eventId) {
        Event eventToCancel = eventRepository.findById(userId).orElseThrow();
        eventToCancel.setState(EventState.CANCELED);
        eventToCancel = eventRepository.save(eventToCancel);
        log.debug("");
        return EventMapper.toEventFullDto(eventToCancel);
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
        return ParticipationRequestMapper.toParticipationRequestDto(requestToConfirm);
    }

    @Override
    public ParticipationRequestDto rejectEventRequest(long userId, long eventId, long reqId) {
        ParticipationRequest requestToReject = requestRepository.findById(reqId).orElseThrow();
        requestToReject.setStatus(ParticipationRequestStatus.REJECTED);
        requestToReject = requestRepository.save(requestToReject);
        log.debug("");
        return ParticipationRequestMapper.toParticipationRequestDto(requestToReject);
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
        return ParticipationRequestMapper.toParticipationRequestDto(newRequest);
    }

    @Override
    public ParticipationRequestDto cancelUserRequest(long userId, long reqId) {
        ParticipationRequest requestToCancel = requestRepository.findById(reqId).orElseThrow();
        requestToCancel.setStatus(ParticipationRequestStatus.REJECTED);
        requestToCancel = requestRepository.save(requestToCancel);
        log.debug("");
        return ParticipationRequestMapper.toParticipationRequestDto(requestToCancel);
    }

    private List<EventShortDto> toEventShortDtos(List<Event> events) {
        return events.stream()
                .map(EventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    private List<ParticipationRequestDto> toParticipationRequestDtos(List<ParticipationRequest> requests) {
        return requests.stream()
                .map(ParticipationRequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    private Event updateEvent(Event event, UpdateEventRequest eventRequest) {
        event.setAnnotation(eventRequest.getAnnotation());
        event.setCategory(categoryRepository.findById(eventRequest.getEventId()).orElseThrow());
        event.setDescription(eventRequest.getDescription());
        event.setEventDate(LocalDateTime.parse(eventRequest.getEventDate(), DateTimeFormatter.ofPattern(DateTimePattern.pattern)));
        event.setPaid(eventRequest.isPaid());
        event.setParticipantLimit(eventRequest.getParticipantLimit());
        event.setTitle(eventRequest.getTitle());
        return event;
    }
}