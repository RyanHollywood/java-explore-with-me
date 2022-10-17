package ru.practicum.ewmservice.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Compilation {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> events;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private boolean pinned;

    @Column
    private String title;
}
