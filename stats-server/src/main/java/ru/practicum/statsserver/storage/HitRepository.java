package ru.practicum.statsserver.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.statsserver.model.Hit;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HitRepository extends JpaRepository<Hit, Long> {
    List<Hit> findHitByTimestampBetween(LocalDateTime start, LocalDateTime end);
    List<Hit> findDistinctByIpHitByTimestampBetween(LocalDateTime start, LocalDateTime end);
    List<Hit> findHitByTimestampBetweenAndUriInOrderByApp(LocalDateTime start, LocalDateTime end, List<String> uris);
    List<Hit> findDistinctByIpHitByTimestampBetweenAndUriInOrderByApp(LocalDateTime start, LocalDateTime end, List<String> uris);
}