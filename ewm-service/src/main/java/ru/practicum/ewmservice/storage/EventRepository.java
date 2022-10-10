package ru.practicum.ewmservice.storage;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmservice.model.Event;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value = "", nativeQuery = true)
    List<Event> getEventsByTextCategoryDateSorted(String text, List<Long> categories, boolean paid, String rangeStart,
                                                  String rangeEnd, boolean onlyAvailable, String sort, PageRequest pageRequest);
}