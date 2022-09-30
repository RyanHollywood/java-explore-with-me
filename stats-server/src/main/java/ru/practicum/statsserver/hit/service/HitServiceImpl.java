package ru.practicum.statsserver.hit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.statsserver.hit.dto.HitDto;
import ru.practicum.statsserver.hit.mapper.HitMapper;
import ru.practicum.statsserver.hit.storage.HitRepository;

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
        return new ResponseEntity<>("Информация сохранена", HttpStatus.OK);
    }
}