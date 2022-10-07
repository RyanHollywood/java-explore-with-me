package ru.practicum.ewmservice.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmservice.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
