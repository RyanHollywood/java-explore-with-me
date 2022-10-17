package ru.practicum.ewmservice.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Hit {
    private String app;
    private String uri;
    private String ip;
    private LocalDateTime timestamp;
}
