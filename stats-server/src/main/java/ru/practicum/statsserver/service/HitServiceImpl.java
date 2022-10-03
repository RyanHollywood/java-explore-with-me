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

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;
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
        log.debug("{} HIT TO {} FROM {} SAVED", hitDto.getApp(), hitDto.getUri(), hitDto.getIp());
        return new ResponseEntity<>("Информация сохранена", HttpStatus.OK);
    }

    @Override
    public List<StatsDto> getStats(String start, String end, List<String> uris, boolean unique) {
        List<StatsDto> statsDtoList;
        if (Optional.ofNullable(uris).isEmpty()) {
            if (unique) {
                log.debug("STATS FROM {} TO {} WITH UNIQUE IP", start, end);
                statsDtoList = toStatsDtos(hitRepository.getAllStatsWithUnigueIp(start, end));
            } else {
                log.debug("STATS FROM {} TO {}", start, end);
                statsDtoList = toStatsDtos(hitRepository.getAllStats(start, end));
            }
        } else {
            if (unique) {
                log.debug("STATS FROM {} TO {} FOR {} WITH UNIQUE IP", start, end, String.join(",", uris));
                statsDtoList = toStatsDtos(hitRepository.getStatsByUrisWithUniqueIp(start, end, uris));
            } else {
                log.debug("STATS FROM {} TO {} FOR {}", start, end, String.join(",", uris));
                statsDtoList = toStatsDtos(hitRepository.getStatsByUris(start, end, uris));
            }
        }
        return fillWithUriWithNoHits(uris, statsDtoList);
    }

    private List<StatsDto> toStatsDtos(List<Tuple> tuples) {
        return tuples.stream()
                .map(StatsMapper::fromTuple)
                .map(StatsMapper::toDto)
                .collect(Collectors.toList());
    }

    private List<StatsDto> fillWithUriWithNoHits(List<String> uris, List<StatsDto> statsDtoList) {
        List<String> statsUris = new ArrayList<>();
        for (StatsDto stats : statsDtoList) {
            statsUris.add(stats.getUri());
        }
        List<String> diffUris = findDifference(uris, statsUris);
        for (String uri : diffUris) {
            statsDtoList.add(new StatsDto("", uri, 0L));
        }
        return statsDtoList.stream()
                .sorted()
                .collect(Collectors.toList());
    }

    private List<String> findDifference(List<String> list1, List<String> list2) {
        return list1.stream()
                .filter(item -> !list2.contains(item))
                .collect(Collectors.toList());
    }
}