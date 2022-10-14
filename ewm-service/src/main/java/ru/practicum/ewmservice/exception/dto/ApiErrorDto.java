package ru.practicum.ewmservice.exception.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiErrorDto {
    private String status;
    private String reason;
    private String message;
    private String timestamp;
}
