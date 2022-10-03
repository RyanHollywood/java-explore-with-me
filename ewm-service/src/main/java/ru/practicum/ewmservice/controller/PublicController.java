package ru.practicum.ewmservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewmservice.dto.category.CategoryDto;
import ru.practicum.ewmservice.dto.compilation.CompilationDto;
import ru.practicum.ewmservice.dto.event.EventFullDto;
import ru.practicum.ewmservice.dto.event.EventShortDto;
import ru.practicum.ewmservice.service.publicewm.PublicServiceImpl;

import java.util.List;

@RestController
@RequestMapping
public class PublicController {

    final PublicServiceImpl publicService;

    public PublicController(PublicServiceImpl publicService) {
        this.publicService = publicService;
    }

    @GetMapping("/events")
    public List<EventShortDto> getEvents() {
        return null;
    }

    @GetMapping("/events/{id}")
    public EventFullDto getEvent(@PathVariable long id) {
        return null;
    }

    @GetMapping("/compilations")
    public List<CompilationDto> getCompilations() {
        return null;
    }

    @GetMapping("/compilations/{compId}")
    public CompilationDto getCompilation(@PathVariable long compId) {
        return null;
    }

    @GetMapping("/categories")
    public List<CategoryDto> getCategories() {
        return null;
    }

    @GetMapping("/categories/{catId}")
    public CategoryDto getCategory(@PathVariable long catId) {
        return null;
    }
}