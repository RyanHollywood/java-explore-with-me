package ru.practicum.ewmservice.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Hit {
    private String app;
    private String uri;
    private String ip;
    private LocalDateTime timestamp;
}
