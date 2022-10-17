package ru.practicum.ewmservice.storage;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmservice.model.Event;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value = "SELECT * " +
            "FROM event " +
            "WHERE (LCASE(annotation) LIKE LCASE(CONCAT('%', :text, '%')) OR LCASE(description) LIKE LCASE(CONCAT('%', :text, '%'))) AND " +
            "category_id IN :categories AND " +
            "paid = :paid AND " +
            "AND confirmed_requests < participant_limit AND " +
            "event_date BETWEEN :rangeStart AND :rangeEnd " +
            "ORDER BY event_date ASC",
            nativeQuery = true)
    List<Event> getEventsPublicAvailableOrderByEventDate(String text, List<Long> categories, boolean paid, LocalDateTime rangeStart,
                                                         LocalDateTime rangeEnd, PageRequest pageRequest);

    @Query(value = "SELECT * " +
            "FROM event " +
            "WHERE (LCASE(annotation) LIKE LCASE(CONCAT('%', :text, '%')) OR LCASE(description) LIKE LCASE(CONCAT('%', :text, '%'))) AND " +
            "category_id IN :categories AND " +
            "paid = :paid AND " +
            "AND confirmed_requests < participant_limit AND " +
            "event_date BETWEEN :rangeStart AND :rangeEnd " +
            "ORDER BY views ASC",
            nativeQuery = true)
    List<Event> getEventsPublicAvailableOrderByViews(String text, List<Long> categories, boolean paid, LocalDateTime rangeStart,
                                                     LocalDateTime rangeEnd, PageRequest pageRequest);

    @Query(value = "SELECT * " +
            "FROM event " +
            "WHERE (LCASE(annotation) LIKE LCASE(CONCAT('%', :text, '%')) OR LCASE(description) LIKE LCASE(CONCAT('%', :text, '%'))) AND " +
            "category_id IN :categories AND " +
            "paid = :paid AND " +
            "event_date BETWEEN :rangeStart AND :rangeEnd " +
            "ORDER BY event_date ASC",
            nativeQuery = true)
    List<Event> getEventsPublicAllOrderByEventDate(String text, List<Long> categories, boolean paid, LocalDateTime rangeStart,
                                                   LocalDateTime rangeEnd, PageRequest pageRequest);

    @Query(value = "SELECT * " +
            "FROM event " +
            "WHERE (LCASE(annotation) LIKE LCASE(CONCAT('%', :text, '%')) OR LCASE(description) LIKE LCASE(CONCAT('%', :text, '%'))) AND " +
            "category_id IN :categories AND " +
            "paid = :paid AND " +
            "event_date BETWEEN :rangeStart AND :rangeEnd " +
            "ORDER BY views ASC",
            nativeQuery = true)
    List<Event> getEventsPublicAllOrderByViews(String text, List<Long> categories, boolean paid, LocalDateTime rangeStart,
                                               LocalDateTime rangeEnd, PageRequest pageRequest);

    @Query(value = "SELECT * " +
            "FROM event " +
            "WHERE initiator_id IN :users AND " +
            "state IN :states AND " +
            "category_id IN :categories AND " +
            "event_date BETWEEN :rangeStart AND :rangeEnd",
            nativeQuery = true)
    List<Event> getEventsAdmin(List<Long> users, List<Integer> states, List<Long> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd,
                               PageRequest pageRequest);

    List<Event> findEventsByInitiatorId(long userId, PageRequest pageRequest);

    List<Event> findEventsByIdIn(List<Long> eventIds);
}