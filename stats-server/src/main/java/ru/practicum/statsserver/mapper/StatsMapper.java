package ru.practicum.statsserver.mapper;

import ru.practicum.statsserver.dto.StatsDto;
import ru.practicum.statsserver.model.Stats;

import javax.persistence.Tuple;
import java.math.BigInteger;

public class StatsMapper {
    public static StatsDto toDto(Stats stats) {
        return StatsDto.builder()
                .app(stats.getApp())
                .uri(stats.getUri())
                .hits(stats.getHits())
                .build();
    }

    public static Stats fromTuple(Tuple tuple) {
        return new Stats(tuple.get(0, String.class),
                tuple.get(1, String.class),
                (tuple.get(2, BigInteger.class).longValue()));
    }
}
