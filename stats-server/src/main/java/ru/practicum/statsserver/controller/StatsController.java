package ru.practicum.statsserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.statsserver.dto.HitDto;
import ru.practicum.statsserver.dto.StatsDto;
import ru.practicum.statsserver.service.HitServiceImpl;

import javax.validation.Valid;
import java.util.List;

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
    public List<StatsDto> getStats(@RequestParam String start,
                                   @RequestParam String end,
                                   @RequestParam(required = false) List<String> apps,
                                   @RequestParam(required = false) List<String> uris,
                                   @RequestParam(required = false) boolean unique) {
        return eventService.getStats(start, end, apps, uris, unique);
    }
}