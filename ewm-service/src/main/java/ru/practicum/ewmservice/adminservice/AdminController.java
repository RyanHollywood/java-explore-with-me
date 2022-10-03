package ru.practicum.ewmservice.adminservice;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/events")
    public void getEvents() {
    }

    @PutMapping("/envents/{eventId}")
    public void getEvent() {
    }

    @PatchMapping("/events/{eventId}/publish")
    public void publishEvent() {
    }

    @PatchMapping("/events/{eventId}/reject")
    public void rejectEvent() {
    }

    @PatchMapping("/categories")
    public void patchCategory() {
    }

    @PostMapping("/categories")
    public void postCategory() {
    }

    @DeleteMapping("/categories/{catId}")
    public void deleteCategory() {
    }

    @GetMapping("/users")
    public void getUsers() {
    }

    @PostMapping("/users")
    public void postUser() {
    }

    @DeleteMapping("/users/{userId}")
    public void deleteUser() {
    }

    @PostMapping("/compilations")
    public void postCompilation() {
    }

    @DeleteMapping("/compilations/{compId}")
    public void deleteCompilation() {
    }

    @DeleteMapping("/compilations/{compId}/events/{eventId}")
    public void deleteEventFromCompilation() {
    }

    @PatchMapping("/compilations/{compId}/events/{eventId}")
    public void patchCompilation() {
    }

    @DeleteMapping("/compilations/{compId}/pin")
    public void unpinCompilation() {
    }

    @PatchMapping("/compilations/{compId}/pin")
    public void pinCompilation() {
    }
}
