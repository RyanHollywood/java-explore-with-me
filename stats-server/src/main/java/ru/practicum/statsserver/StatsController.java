package ru.practicum.statsserver;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.statsserver.dto.HitDto;
import ru.practicum.statsserver.dto.StatsDto;
import ru.practicum.statsserver.service.HitServiceImpl;

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
    public StatsDto getStats(@RequestParam String start,
                             @RequestParam String end,
                             @RequestParam(required = false) String[] uris,
                             @RequestParam(required = false) boolean unique) {
        return null;
    }
}