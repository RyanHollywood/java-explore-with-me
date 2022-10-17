package ru.practicum.ewmservice.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ParticipationRequest {

    @Column
    private LocalDateTime created;

    @ManyToOne
    private Event event;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private User requester;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private ParticipationRequestStatus status;
}