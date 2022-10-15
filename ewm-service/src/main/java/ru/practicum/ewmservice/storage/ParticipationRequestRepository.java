package ru.practicum.ewmservice.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmservice.model.ParticipationRequest;

import java.util.List;

@Repository
public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long> {

    List<ParticipationRequest> findAllByRequesterId(long requsterId);

    List<ParticipationRequest> findAllByEventId(long eventId);
}
