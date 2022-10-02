package ru.practicum.statsserver.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.statsserver.dto.HitDto;
import ru.practicum.statsserver.dto.StatsDto;
import ru.practicum.statsserver.mapper.HitMapper;
import ru.practicum.statsserver.mapper.StatsMapper;
import ru.practicum.statsserver.storage.HitRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class HitServiceImpl implements HitService {

    private final HitRepository hitRepository;

    @Autowired
    public HitServiceImpl(HitRepository hitRepository) {
        this.hitRepository = hitRepository;
    }

    @Override
    public ResponseEntity<String> create(HitDto hitDto) {
        hitRepository.save(HitMapper.fromDto(hitDto));
        log.debug("Информация сохранена");
        return new ResponseEntity<>("Информация сохранена", HttpStatus.OK);
    }

    @Override
    public List<StatsDto> getStats(String start, String end, List<String> uris, boolean unique) {
        if (Optional.ofNullable(uris).isEmpty()) {
            if (unique) {
                return hitRepository.getAllStatsWithUnigueIp(start, end).stream()
                        .map(StatsMapper::fromTuple)
                        .map(StatsMapper::toDto)
                        .collect(Collectors.toList());
            } else {
                return hitRepository.getAllStats(start, end).stream()
                        .map(StatsMapper::fromTuple)
                        .map(StatsMapper::toDto)
                        .collect(Collectors.toList());
            }
        } else {
            return null;
        }
    }

    private LocalDateTime parseDatetime(String datetime) {
        return LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}