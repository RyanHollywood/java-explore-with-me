package ru.practicum.ewmservice.controller;

import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.dto.event.EventFullDto;
import ru.practicum.ewmservice.dto.event.EventShortDto;
import ru.practicum.ewmservice.dto.event.NewEventDto;
import ru.practicum.ewmservice.dto.event.UpdateEventRequest;
import ru.practicum.ewmservice.dto.request.ParticipationRequestDto;
import ru.practicum.ewmservice.service.privateewm.PrivateServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/users")
public class PrivateController {

    final PrivateServiceImpl privateService;

    public PrivateController(PrivateServiceImpl privateService) {
        this.privateService = privateService;
    }

    @GetMapping("/{userId}/events")
    public List<EventShortDto> getEvents(@PathVariable long userId) {
        return null;
    }

    @PatchMapping("/{userId}/events")
    public EventFullDto patchEvents(@PathVariable long userId,
                                    @RequestBody UpdateEventRequest eventRequest) {
        return null;
    }

    @PostMapping("/{userId}/events")
    public EventFullDto postEvents(@PathVariable long userId,
                           @RequestBody NewEventDto eventDto) {
        return null;
    }

    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto getUserEvent(@PathVariable long userId,
                             @PathVariable long eventId) {
        return null;
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public EventFullDto patchUserEvents(@PathVariable long userId,
                                @PathVariable long eventId) {
        return null;
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getEventRequests(@PathVariable long userId,
                                                          @PathVariable long eventId) {
        return null;
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId]/confirm")
    public ParticipationRequestDto confirmEventRequest(@PathVariable long userId,
                                    @PathVariable long eventId,
                                    @PathVariable long reqId) {
        return null;
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId]/reject")
    public ParticipationRequestDto rejectEventRequest(@PathVariable long userId,
                                   @PathVariable long eventId,
                                   @PathVariable long reqId) {
        return null;
    }

    @GetMapping("/{userId}/requests")
    public List<ParticipationRequestDto> getUserRequests(@PathVariable long userId) {
        return null;
    }

    @PostMapping("/{userId}/requests")
    public ParticipationRequestDto postUserRequests(@PathVariable long userId) {
        return null;
    }

    @PatchMapping("/{userId}/requests/{reqId}/cancel")
    public ParticipationRequestDto cancelUserRequest(@PathVariable long userId,
                                  @PathVariable long reqId) {
        return null;
    }
}