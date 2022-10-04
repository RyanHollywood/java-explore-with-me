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
import ru.practicum.statsserver.model.Stats;
import ru.practicum.statsserver.storage.HitRepository;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.Comparator;
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
        log.debug("{} HIT TO {} FROM {} SAVED", hitDto.getApp(), hitDto.getUri(), hitDto.getIp());
        return new ResponseEntity<>("Информация сохранена", HttpStatus.OK);
    }

    @Override
    public List<StatsDto> getStats(String start, String end, List<String> apps, List<String> uris, boolean unique) {
        //need solution for apps and uris - registration table for every app?
        List<Stats> statsList;
        if (Optional.ofNullable(uris).isEmpty()) {
            if (unique) {
                log.debug("STATS FROM {} TO {} WITH UNIQUE IP", start, end);
                statsList = toStats(hitRepository.getAllStatsWithUnigueIp(start, end));
            } else {
                log.debug("STATS FROM {} TO {}", start, end);
                statsList = toStats(hitRepository.getAllStats(start, end));
            }
        } else {
            if (unique) {
                log.debug("STATS FROM {} TO {} FOR {} WITH UNIQUE IP", start, end, String.join(",", uris));
                statsList = toStats(hitRepository.getStatsByUrisWithUniqueIp(start, end, uris));
            } else {
                log.debug("STATS FROM {} TO {} FOR {}", start, end, String.join(",", uris));
                statsList = toStats(hitRepository.getStatsByUris(start, end, uris));
            }
        }
        statsList = fillWithWithNoHits(apps, uris, statsList);
        return toStatsDtoList(statsList);
    }

    private List<Stats> toStats(List<Tuple> tuples) {
        return tuples.stream()
                .map(StatsMapper::fromTuple)
                .collect(Collectors.toList());
    }

    private List<StatsDto> toStatsDtoList(List<Stats> statsList) {
        return statsList.stream()
                .map(StatsMapper::toDto)
                .collect(Collectors.toList());
    }

    private List<Stats> fillWithWithNoHits(List<String> apps, List<String> uris, List<Stats> statsList) {
        List<String> statsApps = new ArrayList<>();
        List<String> statsUris = new ArrayList<>();
        for (Stats stats : statsList) {
            statsUris.add(stats.getUri());
        }
        List<String> diffUris = findDifference(uris, statsUris);
        for (String uri : diffUris) {
            statsList.add(new Stats("", uri, 0));
        }
        statsList.sort(Comparator.naturalOrder());
        return statsList;
    }

    private List<String> findDifference(List<String> list1, List<String> list2) {
        return list1.stream()
                .filter(item -> !list2.contains(item))
                .collect(Collectors.toList());
    }
}