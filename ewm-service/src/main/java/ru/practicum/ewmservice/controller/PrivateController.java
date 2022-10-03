package ru.practicum.ewmservice.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class PrivateController {
    @GetMapping("/{userId}/events")
    public void getEvents() {
    }

    @PatchMapping("/{userId}/events")
    public void patchEvents() {
    }

    @PostMapping("/{userId}/events")
    public void postEvents() {
    }

    @GetMapping("/{userId}/events/{eventId}")
    public void getUserEvent() {
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public void patchUserEvents() {
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public void getEventRequests() {
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId]/confirm")
    public void confirmEventRequest() {
    }

    @PatchMapping("/{userId}/events/{eventId}/requests/{reqId]/reject")
    public void rejectEventRequest() {
    }

    @GetMapping("/{userId}/requests")
    public void getUserRequests() {
    }

    @PostMapping("/{userId}/requests")
    public void postUserRequests() {
    }

    @PatchMapping("/{userId}/requests/{reqId}/cancel")
    public void cancelUserRequest() {
    }
}