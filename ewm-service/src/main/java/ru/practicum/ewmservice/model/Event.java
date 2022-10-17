package ru.practicum.ewmservice.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Event {
    @Column(length = 65555)
    private String annotation;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @Column
    private long confirmedRequests;

    @Column
    private LocalDateTime createdOn;

    @Column(length = 65555)
    private String description;

    @Column
    private LocalDateTime eventDate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "initiator_id", referencedColumnName = "id")
    private User initiator;

    @OneToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    @Column
    private boolean paid;

    @Column
    private int participantLimit;

    @Column
    private LocalDateTime publishedOn;

    @Column
    private boolean requestModeration;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private EventState state;

    @Column
    private String title;

    @Column
    private long views;
}
