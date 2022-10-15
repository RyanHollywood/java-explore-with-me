package ru.practicum.ewmservice.mapper;

import ru.practicum.ewmservice.dto.event.EventFullDto;
import ru.practicum.ewmservice.dto.event.EventShortDto;
import ru.practicum.ewmservice.dto.event.NewEventDto;
import ru.practicum.ewmservice.model.Event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class EventMapper {

    public static Event fromNewEventDto(NewEventDto newEventDto, String pattern) {
        return Event.builder()
                .annotation(newEventDto.getAnnotation())
                .description(newEventDto.getDescription())
                .eventDate(LocalDateTime.parse(newEventDto.getEventDate(),
                        DateTimeFormatter.ofPattern(pattern)))
                .location(LocationMapper.toLocation(newEventDto.getLocation()))
                .paid(newEventDto.isPaid())
                .participantLimit(newEventDto.getParticipantLimit())
                .requestModeration(newEventDto.isRequestModeration())
                .title(newEventDto.getTitle())
                .build();
    }

    public static EventFullDto toEventFullDto(Event event, String pattern) {
        return EventFullDto.builder()
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(event.getCreatedOn().format(DateTimeFormatter.ofPattern(pattern)))
                .description(event.getDescription())
                .eventDate(event.getEventDate().format(DateTimeFormatter.ofPattern(pattern)))
                .id(event.getId())
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .location(LocationMapper.toLocationDto(event.getLocation()))
                .paid(event.isPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(getPublishedOn(event, pattern))
                .requestModeration(event.isRequestModeration())
                .state(event.getState().toString())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    public static EventShortDto toEventShortDto(Event event, String pattern) {
        return EventShortDto.builder()
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate().format(DateTimeFormatter.ofPattern(pattern)))
                .id(event.getId())
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .paid(event.isPaid())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    private static String getPublishedOn(Event event, String pattern) {
        if (Optional.ofNullable(event.getPublishedOn()).isPresent()) {
            return event.getPublishedOn().format(DateTimeFormatter.ofPattern(pattern));
        }
        return null;
    }
}