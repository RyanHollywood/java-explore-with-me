package ru.practicum.statsserver.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.statsserver.model.Hit;

@Repository
public interface HitRepository extends JpaRepository<Hit, Long> {
}