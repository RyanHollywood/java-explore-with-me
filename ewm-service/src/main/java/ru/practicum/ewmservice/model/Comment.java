package ru.practicum.ewmservice.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Comment {
    private long id;
    private Event event;
    private User author;
    private String text;
    private LocalDateTime created;
}
