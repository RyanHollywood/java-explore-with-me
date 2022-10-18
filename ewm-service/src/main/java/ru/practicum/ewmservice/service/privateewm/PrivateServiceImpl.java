package ru.practicum.ewmservice.service.privateewm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.dto.comment.CommentDto;
import ru.practicum.ewmservice.dto.comment.NewCommentDto;
import ru.practicum.ewmservice.dto.event.EventFullDto;
import ru.practicum.ewmservice.dto.event.EventShortDto;
import ru.practicum.ewmservice.dto.event.NewEventDto;
import ru.practicum.ewmservice.dto.event.UpdateEventRequest;
import ru.practicum.ewmservice.dto.request.ParticipationRequestDto;
import ru.practicum.ewmservice.exception.model.BadRequest;
import ru.practicum.ewmservice.exception.model.NotFound;
import ru.practicum.ewmservice.mapper.CommentMapper;
import ru.practicum.ewmservice.mapper.EventMapper;
import ru.practicum.ewmservice.mapper.ParticipationRequestMapper;
import ru.practicum.ewmservice.model.*;
import ru.practicum.ewmservice.storage.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
public class PrivateServiceImpl implements PrivateService {

    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ParticipationRequestRepository requestRepository;
    private final LocationRepository locationRepository;
    private final CommentRepository commentRepository;
    private final ParticipationRequestStatus defaultStatus = ParticipationRequestStatus.PENDING;
    private final String pattern;

    public PrivateServiceImpl(EventRepository eventRepository, CategoryRepository categoryRepository, UserRepository userRepository,
                              ParticipationRequestRepository requestRepository, LocationRepository locationRepository, CommentRepository commentRepository, @Value("${date.time.pattern}") String pattern) {
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.requestRepository = requestRepository;
        this.locationRepository = locationRepository;
        this.commentRepository = commentRepository;
        this.pattern = pattern;
    }

    @Override
    public List<EventShortDto> getEvents(long userId, int from, int size) {
        List<Event> events = eventRepository.findEventsByInitiatorId(userId, PageRequest.of(from / size, size));
        log.debug("Events by user id={} were found.", userId);
        return toEventShortDtos(events);
    }

    @Transactional
    @Override
    public EventFullDto updateEvent(long userId, UpdateEventRequest eventRequest) {
        Event eventToUpdate = getEvent(eventRequest.getEventId());
        eventToUpdate = updateEvent(eventToUpdate, eventRequest);
        eventToUpdate = eventRepository.save(eventToUpdate);
        log.debug("Event with id={} was updated.", eventToUpdate.getId());
        return EventMapper.toEventFullDto(eventToUpdate, pattern);
    }

    @Transactional
    @Override
    public EventFullDto postEvent(long userId, NewEventDto newEventDto) {
        Event newEvent = EventMapper.fromNewEventDto(newEventDto, pattern);
        newEvent.setInitiator(getUser(userId));
        newEvent.setState(EventState.PENDING);
        Location newEventLocation = locationRepository.save(newEvent.getLocation());
        Category newEventCategory = getCategory(newEventDto.getCategory());
        newEvent.setLocation(newEventLocation);
        newEvent.setCategory(newEventCategory);
        newEvent.setCreatedOn(LocalDateTime.now());
        if (Duration.between(newEvent.getCreatedOn(), newEvent.getEventDate()).toHours() <= 2) {
            throw new BadRequest("Creating event 2 hours before start is not allowed.");
        }
        newEvent = eventRepository.save(newEvent);
        log.debug("Event with id={} was posted.", newEvent.getId());
        return EventMapper.toEventFullDto(newEvent, pattern);
    }

    @Override
    public EventFullDto getUserEvent(long userId, long eventId) {
        Event event = getEvent(eventId);
        viewsCounter(event);
        log.debug("Event with id={} was found.", eventId);
        return EventMapper.toEventFullDto(event, pattern);
    }

    @Transactional
    @Override
    public EventFullDto cancelUserEvent(long userId, long eventId) {
        Event eventToCancel = getEvent(eventId);
        if (eventToCancel.getState().equals(EventState.PENDING)) {
            eventToCancel.setState(EventState.CANCELED);
        }
        eventToCancel = eventRepository.save(eventToCancel);
        log.debug("Event with id={} wad cancelled.", eventId);
        return EventMapper.toEventFullDto(eventToCancel, pattern);
    }

    @Override
    public List<ParticipationRequestDto> getEventRequests(long userId, long eventId) {
        List<ParticipationRequest> requests = requestRepository.findAllByEventId(eventId);
        log.debug("Requests for event id={} were found.", eventId);
        return toParticipationRequestDtos(requests);
    }

    @Transactional
    @Override
    public ParticipationRequestDto confirmEventRequest(long userId, long eventId, long reqId) {
        ParticipationRequest requestToConfirm = getRequest(reqId);
        Event event = getEvent(eventId);
        if (event.getConfirmedRequests() < event.getParticipantLimit()) {
            requestToConfirm.setStatus(ParticipationRequestStatus.CONFIRMED);
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            eventRepository.save(event);
        } else {
            throw new BadRequest("Event participation limit has reached, request cannot be confirmed");
        }
        requestToConfirm = requestRepository.save(requestToConfirm);
        log.debug("Request with id={} was confirmed.", reqId);
        return ParticipationRequestMapper.toParticipationRequestDto(requestToConfirm, pattern);
    }

    @Transactional
    @Override
    public ParticipationRequestDto rejectEventRequest(long userId, long eventId, long reqId) {
        ParticipationRequest requestToReject = getRequest(reqId);
        requestToReject.setStatus(ParticipationRequestStatus.REJECTED);
        requestToReject = requestRepository.save(requestToReject);
        log.debug("Request with id={} was rejected.", reqId);
        return ParticipationRequestMapper.toParticipationRequestDto(requestToReject, pattern);
    }

    @Override
    public List<ParticipationRequestDto> getUserRequests(long userId) {
        List<ParticipationRequest> requests = requestRepository.findAllByRequesterId(userId);
        log.debug("User with id={} requests were found.", userId);
        return toParticipationRequestDtos(requests);
    }

    @Transactional
    @Override
    public ParticipationRequestDto postUserRequest(long userId, long eventId) {
        ParticipationRequest newRequest = ParticipationRequest.builder()
                .created(LocalDateTime.now())
                .event(getEvent(eventId))
                .requester(getUser(userId))
                .build();
        Event event = getEvent(eventId);
        if (event.isRequestModeration()) {
            newRequest.setStatus(defaultStatus);
        } else {
            newRequest.setStatus(ParticipationRequestStatus.CONFIRMED);
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            eventRepository.save(event);
        }
        newRequest = requestRepository.save(newRequest);
        log.debug("User with id={} request to event id={} was posted", userId, eventId);
        return ParticipationRequestMapper.toParticipationRequestDto(newRequest, pattern);
    }

    @Transactional
    @Override
    public ParticipationRequestDto cancelUserRequest(long userId, long reqId) {
        ParticipationRequest requestToCancel = getRequest(reqId);
        requestToCancel.setStatus(ParticipationRequestStatus.CANCELED);
        requestToCancel = requestRepository.save(requestToCancel);
        log.debug("Request with id={} was cancelled.", reqId);
        return ParticipationRequestMapper.toParticipationRequestDto(requestToCancel, pattern);
    }

    @Override
    public CommentDto postComment(long userId, long eventId, NewCommentDto newCommentDto) {
        User author = getUser(userId);
        Event event = getEvent(eventId);
        Comment comment = CommentMapper.toComment(newCommentDto);
        comment.setEvent(event);
        comment.setAuthor(author);
        comment.setCreated(LocalDateTime.now());
        comment = commentRepository.save(comment);
        log.debug("Comment from user id={} to event id={} with id={} was posted.", userId, event, comment.getId());
        return CommentMapper.toCommentDto(comment, pattern);
    }

    @Override
    public List<CommentDto> getComments(long userId, long eventId) {
        Event event = getEvent(eventId);
        List<Comment> comments = commentRepository.findAllByEvent(event);
        log.debug("Comments to event with id={} were found", eventId);
        return toCommentDtos(comments);
    }

    @Override
    public CommentDto getComment(long userId, long eventId, long comId) {
        getEvent(eventId);
        Comment comment = getComment(comId);
        log.debug("Comment with id={} was found.", comId);
        return CommentMapper.toCommentDto(comment, pattern);
    }

    @Override
    public void deleteComment(long userId, long eventId, long comId) {
        getEvent(eventId);
        Comment comment = getComment(comId);
        if (comment.getAuthor().getId() != userId) {
            throw new BadRequest("User cannot delete other user comments");
        }
        commentRepository.deleteById(comId);
        log.debug("Comment with id:{} was deleted", comId);
    }

    private void viewsCounter(Event event) {
        event.setViews(event.getViews() + 1);
        log.debug("Event with id={} views counter increased.", event.getId());
        eventRepository.save(event);
    }

    private List<EventShortDto> toEventShortDtos(List<Event> events) {
        return events.stream()
                .map(event -> EventMapper.toEventShortDto(event, pattern))
                .collect(Collectors.toList());
    }

    private List<ParticipationRequestDto> toParticipationRequestDtos(List<ParticipationRequest> requests) {
        return requests.stream()
                .map(request -> ParticipationRequestMapper.toParticipationRequestDto(request, pattern))
                .collect(Collectors.toList());
    }

    private List<CommentDto> toCommentDtos(List<Comment> comments) {
        return comments.stream()
                .map(comment -> CommentMapper.toCommentDto(comment, pattern))
                .collect(Collectors.toList());
    }

    private Event updateEvent(Event event, UpdateEventRequest eventRequest) {
        event.setAnnotation(eventRequest.getAnnotation());
        event.setDescription(eventRequest.getDescription());
        event.setEventDate(LocalDateTime.parse(eventRequest.getEventDate(), DateTimeFormatter.ofPattern(pattern)));
        event.setPaid(eventRequest.isPaid());
        event.setParticipantLimit(eventRequest.getParticipantLimit());
        event.setTitle(eventRequest.getTitle());
        return event;
    }

    private User getUser(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> {
            log.warn("User with id={} was not found.", userId);
            throw new NotFound("User with id=" + userId + " was not found.");
        });
    }

    private Category getCategory(long catId) {
        return categoryRepository.findById(catId).orElseThrow(() -> {
            log.warn("Category with id={} was not found.", catId);
            throw new NotFound("Category with id=" + catId + " was not found.");
        });
    }

    private Event getEvent(long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> {
            log.warn("Event with id={} was not found.", eventId);
            throw new NotFound("Event with id=" + eventId + " was not found.");
        });
    }

    private ParticipationRequest getRequest(long reqId) {
        return requestRepository.findById(reqId).orElseThrow(() -> {
            log.warn("Participation request with id={} was not found.", reqId);
            throw new NotFound("Participation request with id=" + reqId + " was not found.");
        });
    }

    private Comment getComment(long comId) {
        return commentRepository.findById(comId).orElseThrow(() -> {
            log.warn("Comment with id={} was not found.", comId);
            throw new NotFound("Comment with id=" + comId + " was not found.");
        });
    }
}