package ru.practicum.ewmservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ParticipationRequest {

    @Column
    private LocalDateTime created;

    @ManyToOne
    @Column
    @JoinTable(name = "event")
    private Event event;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @Column
    @JoinTable(name = "user")
    private User requester;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private ParticipationRequestStatus status;
}