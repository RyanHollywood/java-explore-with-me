package ru.practicum.ewmservice.service.privateewm;

import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.dto.event.EventFullDto;
import ru.practicum.ewmservice.dto.event.EventShortDto;
import ru.practicum.ewmservice.dto.event.NewEventDto;
import ru.practicum.ewmservice.dto.event.UpdateEventRequest;
import ru.practicum.ewmservice.dto.partition.ParticipationRequestDto;

import java.util.List;

@Service
public class PrivateServiceImpl implements PrivateService {
    @Override
    public List<EventShortDto> getEvents(long userId) {
        return null;
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
}