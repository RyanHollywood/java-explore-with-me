package ru.practicum.statsserver.event.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.statsserver.event.dto.EventDto;
import ru.practicum.statsserver.event.mapper.EventMapper;
import ru.practicum.statsserver.event.storage.EventRepository;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public ResponseEntity<String> create(EventDto eventDto) {
        eventRepository.save(EventMapper.fromDto(eventDto));
        return new ResponseEntity<>("Информация сохранена", HttpStatus.OK);
    }
}