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
    private long eventId;
    private long author;
    private String text;
    private LocalDateTime created;
}
