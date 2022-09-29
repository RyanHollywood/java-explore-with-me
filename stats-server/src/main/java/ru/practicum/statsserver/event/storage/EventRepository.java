package ru.practicum.statsserver.event.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.statsserver.event.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}