package ru.practicum.ewmservice.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParticipationRequestDto {
    private String created;
    private long event;
    private long id;
    private long requester;
    private String status;
}
