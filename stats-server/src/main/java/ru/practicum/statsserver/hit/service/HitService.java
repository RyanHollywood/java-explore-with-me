package ru.practicum.statsserver.hit.service;

import org.springframework.http.ResponseEntity;
import ru.practicum.statsserver.hit.dto.HitDto;

public interface HitService {
    ResponseEntity<String> create(HitDto hitDto);
}