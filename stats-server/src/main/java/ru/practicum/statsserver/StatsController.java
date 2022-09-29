package ru.practicum.statsserver;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.statsserver.event.dto.EventDto;
import ru.practicum.statsserver.event.service.EventServiceImpl;

import javax.validation.Valid;

@RestController
public class StatsController {

    final EventServiceImpl eventService;

    public StatsController(EventServiceImpl eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/hit")
    public ResponseEntity<String> create(@Valid @RequestBody EventDto eventDto) {
        return eventService.create(eventDto);
    }

    @GetMapping("/stats")
    public void getStats() {

    }
}