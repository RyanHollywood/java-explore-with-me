package ru.practicum.ewmservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class PublicController {
    @GetMapping("/events")
    public void getEvents() {
    }

    @GetMapping("/events/{id}")
    public void getEvent() {
    }

    @GetMapping("/compilations")
    public void getCompilations() {
    }

    @GetMapping("/compilations/{compId}")
    public void getCompilation() {
    }

    @GetMapping("/categories")
    public void getCategories() {
    }

    @GetMapping("/categories/{catId}")
    public void getCategory() {
    }
}