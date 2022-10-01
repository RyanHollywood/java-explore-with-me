package ru.practicum.statsserver.service;

import org.springframework.http.ResponseEntity;
import ru.practicum.statsserver.dto.HitDto;

public interface HitService {
    ResponseEntity<String> create(HitDto hitDto);
}