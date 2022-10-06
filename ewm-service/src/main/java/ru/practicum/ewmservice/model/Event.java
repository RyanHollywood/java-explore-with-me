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
@Table(name = "events")
public class Event {
    @Column
    private String annotation;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @Column
    private long confirmedRequests;

    @Column
    private LocalDateTime createOn;

    @Column
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
    private String publishedOn;

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
