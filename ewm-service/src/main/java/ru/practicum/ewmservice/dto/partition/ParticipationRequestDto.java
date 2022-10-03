package ru.practicum.ewmservice.dto.partition;

import lombok.Data;

@Data
public class ParticipationRequestDto {
    private String created;
    private long event;
    private long id;
    private long requester;
    private String status;
}
