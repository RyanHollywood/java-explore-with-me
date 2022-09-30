package ru.practicum.statsserver;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.statsserver.hit.dto.HitDto;
import ru.practicum.statsserver.hit.service.HitServiceImpl;

import javax.validation.Valid;

@RestController
public class StatsController {

    final HitServiceImpl eventService;

    public StatsController(HitServiceImpl eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/hit")
    public ResponseEntity<String> create(@Valid @RequestBody HitDto hitDto) {
        return eventService.create(hitDto);
    }

    @GetMapping("/stats")
    public void getStats() {

    }
}