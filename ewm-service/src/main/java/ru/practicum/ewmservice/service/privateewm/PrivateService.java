package ru.practicum.ewmservice.service.privateewm;

import ru.practicum.ewmservice.dto.event.EventFullDto;
import ru.practicum.ewmservice.dto.event.EventShortDto;
import ru.practicum.ewmservice.dto.event.NewEventDto;
import ru.practicum.ewmservice.dto.event.UpdateEventRequest;
import ru.practicum.ewmservice.dto.request.ParticipationRequestDto;

import java.util.List;

public interface PrivateService {

    List<EventShortDto> getEvents(long userId, int from, int size);

    EventFullDto updateEvent(long userId, UpdateEventRequest eventRequest);

    EventFullDto postEvent(long userId, NewEventDto newEventDto);

    EventFullDto getUserEvent(long userId, long eventId);

    EventFullDto cancelUserEvent(long userId, long eventId);

    List<ParticipationRequestDto> getEventRequests(long userId, long eventId);

    ParticipationRequestDto confirmEventRequest(long userId, long eventId, long reqId);

    ParticipationRequestDto rejectEventRequest(long userId, long eventId, long reqId);

    List<ParticipationRequestDto> getUserRequests(long userId);

    ParticipationRequestDto postUserRequest(long userId, long eventId);

    ParticipationRequestDto cancelUserRequest(long userId, long reqId);
}