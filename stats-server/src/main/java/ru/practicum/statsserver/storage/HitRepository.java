package ru.practicum.statsserver.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.statsserver.model.Hit;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface HitRepository extends JpaRepository<Hit, Long> {
    @Query(value = "SELECT app, uri, COUNT(ip)" +
            "FROM (SELECT *" +
            "      FROM hit" +
            "      WHERE timestamp BETWEEN CAST(:start AS DATETIME) AND CAST(:end AS DATETIME)) as hits_formatted" +
            " GROUP BY app, uri;", nativeQuery = true)
    List<Tuple> getAllStats(@Param("start") String start,
                            @Param("end") String end);

    @Query(value = "SELECT app, uri, COUNT(ip)" +
            "FROM (SELECT DISTINCT app, uri, ip" +
            "      FROM hit" +
            "      WHERE timestamp BETWEEN CAST(:start AS DATETIME) AND CAST(:end AS DATETIME)) as hits_formatted" +
            " GROUP BY app, uri;", nativeQuery = true)
    List<Tuple> getAllStatsWithUnigueIp(@Param("start") String start,
                                        @Param("end") String end);

    @Query(value = "SELECT app, uri, COUNT(ip)" +
            "FROM (SELECT *" +
            "      FROM hit" +
            "      WHERE timestamp BETWEEN CAST(:start AS DATETIME) AND CAST(:end AS DATETIME)) as hits_formatted " +
            "WHERE uri in (:uris)" +
            "GROUP BY app, uri;", nativeQuery = true)
    List<Tuple> getStatsByUris(@Param("start") String start,
                               @Param("end") String end,
                               @Param("uris") List<String> uris);

    @Query(value = "SELECT app, uri, COUNT(ip)" +
            "FROM (SELECT DISTINCT app, uri, ip" +
            "      FROM hit" +
            "      WHERE timestamp BETWEEN CAST(:start AS DATETIME) AND CAST(:end AS DATETIME)) as hits_formatted " +
            "WHERE uri in (:uris)" +
            "GROUP BY app, uri;", nativeQuery = true)
    List<Tuple> getStatsByUrisWithUniqueIp(@Param("start") String start,
                                           @Param("end") String end,
                                           @Param("uris") List<String> uris);

    @Query(value = "SELECT app, uri, COUNT(ip)" +
            "FROM (SELECT *" +
            "      FROM hit" +
            "      WHERE timestamp BETWEEN CAST(:start AS DATETIME) AND CAST(:end AS DATETIME)) as hits_formatted " +
            "WHERE app in (:apps) AND uri in (:uris)" +
            "GROUP BY app, uri;", nativeQuery = true)
    List<Tuple> getStatsByAppAndUris(@Param("start") String start,
                                     @Param("end") String end,
                                     @Param("apps") List<String> apps,
                                     @Param("uris") List<String> uris);

    @Query(value = "SELECT app, uri, COUNT(ip)" +
            "FROM (SELECT DISTINCT app, uri, ip" +
            "      FROM hit" +
            "      WHERE timestamp BETWEEN CAST(:start AS DATETIME) AND CAST(:end AS DATETIME)) as hits_formatted " +
            "WHERE app in (:apps) AND uri in (:uris)" +
            "GROUP BY app, uri;", nativeQuery = true)
    List<Tuple> getStatsByAppsAndUrisWithUniqueIp(@Param("start") String start,
                                                  @Param("end") String end,
                                                  @Param("apps") List<String> apps,
                                                  @Param("uris") List<String> uris);
}