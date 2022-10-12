package ru.practicum.ewmservice.storage;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmservice.model.Event;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value = "SELECT * " +
            "FROM event " +
            "WHERE (LCASE(annotation) LIKE LCASE(CONCAT('%', :text, '%')) OR LCASE(description) LIKE LCASE(CONCAT('%', :text, '%'))) AND " +
            "category_id IN :categories AND " +
            "paid = :paid AND " +
            "AND confirmed_requests < participant_limit AND " +
            "event_date BETWEEN CAST(:rangeStart AS DATETIME) AND CAST(:rangeEnd AS DATETIME) AND " +
            "ORDER BY :sort;"
            , nativeQuery = true)
    List<Event> getEventsPublicAvailable(String text, List<Long> categories, boolean paid, String rangeStart,
                                                  String rangeEnd, String sort, PageRequest pageRequest);

    @Query(value = "SELECT * " +
            "FROM event " +
            "WHERE (LCASE(annotation) LIKE LCASE(CONCAT('%', :text, '%')) OR LCASE(description) LIKE LCASE(CONCAT('%', :text, '%'))) AND " +
            "category_id IN :categories AND " +
            "paid = :paid AND " +
            "event_date BETWEEN CAST(:rangeStart AS DATETIME) AND CAST(:rangeEnd AS DATETIME) AND " +
            "ORDER BY :sort;"
            , nativeQuery = true)
    List<Event> getEventsPublicAll(String text, List<Long> categories, boolean paid, String rangeStart,
                                         String rangeEnd, String sort, PageRequest pageRequest);
}