package ru.practicum.statsserver.event.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @Column
    private String app;
    @Column
    private String uri;
    @Column
    private String ip;
    @Column
    private LocalDateTime timestamp;
}
