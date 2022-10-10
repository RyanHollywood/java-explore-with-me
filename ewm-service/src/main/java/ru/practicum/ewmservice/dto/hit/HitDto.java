package ru.practicum.ewmservice.dto.hit;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class HitDto {
    private String app;
    private String uri;
    private String ip;
    private String timestamp;
}