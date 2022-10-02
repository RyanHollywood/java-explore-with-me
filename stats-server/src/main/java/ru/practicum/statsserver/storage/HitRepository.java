package ru.practicum.statsserver.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.statsserver.model.Hit;
import ru.practicum.statsserver.model.Stats;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface HitRepository extends JpaRepository<Hit, Long> {
    @Query(value = "SELECT app, uri, COUNT(ip)" +
            "FROM (SELECT *" +
            "      FROM hits" +
            "      WHERE timestamp BETWEEN CAST(:start AS DATETIME) AND CAST(:end AS DATETIME)) as hits_formatted" +
            " GROUP BY app, uri;", nativeQuery = true)
    List<Tuple> getAllStats(@Param("start") String start,
                            @Param("end") String end);
    /*
    @Query()
    List<Object> getAllStatsWithUnigueIp(@Param("start") String start,
                                         @Param("end") String end);

    @Query()
    List<Object> getStatsByUris(@Param("start") String start,
                                @Param("end") String end,
                                @Param("uris") List<String> uris);

    @Query()
    List<Object> getStatsByUrisWithUniqueIp(@Param("start") String start,
                                            @Param("end") String end,
                                            @Param("uris") List<String> uris);
    */
    /*
    List<Hit> findHitByTimestampBetweenOrderByApp(LocalDateTime start, LocalDateTime end);
    List<Hit> findDistinctByIpHitByTimestampBetween(LocalDateTime start, LocalDateTime end);
    List<Hit> findHitByTimestampBetweenAndUriInOrderByApp(LocalDateTime start, LocalDateTime end, List<String> uris);
    List<Hit> findDistinctByIpHitByTimestampBetweenAndUriInOrderByApp(LocalDateTime start, LocalDateTime end, List<String> uris);
     */
}