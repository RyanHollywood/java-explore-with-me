package ru.practicum.statsserver.service;

import org.springframework.http.ResponseEntity;
import ru.practicum.statsserver.dto.HitDto;
import ru.practicum.statsserver.dto.StatsDto;

import java.util.List;

public interface HitService {
    ResponseEntity<String> create(HitDto hitDto);

    List<StatsDto> getStats(String start, String end, List<String> uris, boolean unique);
}