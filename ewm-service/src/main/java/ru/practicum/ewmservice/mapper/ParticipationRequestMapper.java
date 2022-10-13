package ru.practicum.ewmservice.mapper;

import ru.practicum.ewmservice.dto.request.ParticipationRequestDto;
import ru.practicum.ewmservice.model.ParticipationRequest;

import java.time.format.DateTimeFormatter;

public class ParticipationRequestMapper {
    public static ParticipationRequestDto toParticipationRequestDto(ParticipationRequest request) {
        return ParticipationRequestDto.builder()
                .created(request.getCreated().format(DateTimeFormatter.ofPattern(DateTimePattern.pattern)))
                .event(request.getEvent().getId())
                .id(request.getId())
                .requester(request.getRequester().getId())
                .status(request.getStatus().toString())
                .build();
    }
}