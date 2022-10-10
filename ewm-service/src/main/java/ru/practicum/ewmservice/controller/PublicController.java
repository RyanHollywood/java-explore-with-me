package ru.practicum.ewmservice.controller;

import org.springframework.web.bind.annotation.*;
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
    public List<EventShortDto> getEvents(@RequestParam String text,
                                         @RequestParam List<Long> categories,
                                         @RequestParam boolean paid,
                                         @RequestParam String rangeStart,
                                         @RequestParam String rangeEnd,
                                         @RequestParam boolean onlyAvailable,
                                         @RequestParam String sort,
                                         @RequestParam int from,
                                         @RequestParam(defaultValue = "10") int size) {
        return publicService.getEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
    }

    @GetMapping("/events/{id}")
    public EventFullDto getEvent(@PathVariable long id) {
        return publicService.getEvent(id);
    }

    @GetMapping("/compilations")
    public List<CompilationDto> getCompilations(@RequestParam boolean pinned,
                                                @RequestParam int from,
                                                @RequestParam(defaultValue = "10") int size) {
        return publicService.getCompilations(pinned, from, size);
    }

    @GetMapping("/compilations/{compId}")
    public CompilationDto getCompilation(@PathVariable long compId) {
        return publicService.getCompilation(compId);
    }

    @GetMapping("/categories")
    public List<CategoryDto> getCategories(@RequestParam int from,
                                           @RequestParam(defaultValue = "10") int size) {
        return publicService.getCategories(from, size);
    }

    @GetMapping("/categories/{catId}")
    public CategoryDto getCategory(@PathVariable long catId) {
        return publicService.getCategory(catId);
    }
}