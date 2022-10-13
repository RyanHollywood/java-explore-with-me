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
    public List<EventShortDto> getEvents(@PathVariable long userId,
                                         @RequestParam int from,
                                         @RequestParam(defaultValue = "10") int size) {
        return privateService.getEvents(userId, from, size);
    }

    @PatchMapping("/{userId}/events")
    public EventFullDto patchEvents(@PathVariable long userId,
                                    @RequestBody UpdateEventRequest eventRequest) {
        return privateService.updateEvent(userId, eventRequest);
    }

    @PostMapping("/{userId}/events")
    public EventFullDto postEvents(@PathVariable long userId,
                                   @RequestBody NewEventDto eventDto) {
        return privateService.postEvent(userId, eventDto);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto getUserEvent(@PathVariable long userId,
                                     @PathVariable long eventId) {
        return privateService.getUserEvent(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public EventFullDto cancelUserEvent(@PathVariable long userId,
                                        @PathVariable long eventId) {
        return privateService.cancelUserEvent(userId, eventId);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getEventRequests(@PathVariable long userId,
                                                          @PathVariable long eventId) {
        return privateService.getEventRequests(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmEventRequest(@PathVariable long userId,
                                                       @PathVariable long eventId,
                                                       @PathVariable long reqId) {
        return privateService.confirmEventRequest(userId, eventId, reqId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectEventRequest(@PathVariable long userId,
                                                      @PathVariable long eventId,
                                                      @PathVariable long reqId) {
        return privateService.rejectEventRequest(userId, eventId, reqId);
    }

    @GetMapping("/{userId}/requests")
    public List<ParticipationRequestDto> getUserRequests(@PathVariable long userId) {
        return privateService.getUserRequests(userId);
    }

    @PostMapping("/{userId}/requests")
    public ParticipationRequestDto postUserRequest(@PathVariable long userId,
                                                   @RequestParam long eventId) {
        return privateService.postUserRequest(userId, eventId);
    }

    @PatchMapping("/{userId}/requests/{reqId}/cancel")
    public ParticipationRequestDto cancelUserRequest(@PathVariable long userId,
                                                     @PathVariable long reqId) {
        return privateService.cancelUserRequest(userId, reqId);
    }
}