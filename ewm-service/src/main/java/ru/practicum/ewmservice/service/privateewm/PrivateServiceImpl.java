package ru.practicum.ewmservice.service.privateewm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.dto.event.EventFullDto;
import ru.practicum.ewmservice.dto.event.EventShortDto;
import ru.practicum.ewmservice.dto.event.NewEventDto;
import ru.practicum.ewmservice.dto.event.UpdateEventRequest;
import ru.practicum.ewmservice.dto.partition.ParticipationRequestDto;
import ru.practicum.ewmservice.mapper.EventMapper;
import ru.practicum.ewmservice.model.Event;
import ru.practicum.ewmservice.storage.EventRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PrivateServiceImpl implements PrivateService {

    private final EventRepository eventRepository;

    public PrivateServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<EventShortDto> getEvents(long userId, int from, int size) {
        List<Event> events = eventRepository.findEventsByInitiatorId(userId, PageRequest.of(from / size, size));
        log.debug("");
        return toEventShortDtos(events);
    }

    @Override
    public EventFullDto updateEvent(long userId, UpdateEventRequest eventRequest) {
        return null;
    }

    @Override
    public EventFullDto postEvent(long userId, NewEventDto newEventDto) {
        return null;
    }

    @Override
    public EventFullDto getUserEvent(long userId, long eventId) {
        return null;
    }

    @Override
    public EventFullDto cancelUserEvent(long userId, long eventId) {
        return null;
    }

    @Override
    public List<ParticipationRequestDto> getEventRequests(long userId, long eventId) {
        return null;
    }

    @Override
    public ParticipationRequestDto confirmEventRequest(long userId, long eventId, long reqId) {
        return null;
    }

    @Override
    public ParticipationRequestDto rejectEventRequest(long userId, long eventId, long reqId) {
        return null;
    }

    @Override
    public List<ParticipationRequestDto> getUserRequests(long userId) {
        return null;
    }

    @Override
    public ParticipationRequestDto postUserRequest(long userId) {
        return null;
    }

    @Override
    public ParticipationRequestDto cancelUserRequest(long userId, long reqId) {
        return null;
    }

    private List<EventShortDto> toEventShortDtos(List<Event> events) {
        return events.stream()
                .map(EventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }
}