package ru.practicum.ewmservice.controller;

import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.dto.category.CategoryDto;
import ru.practicum.ewmservice.dto.category.NewCategoryDto;
import ru.practicum.ewmservice.dto.compilation.CompilationDto;
import ru.practicum.ewmservice.dto.compilation.NewCompilationDto;
import ru.practicum.ewmservice.dto.event.AdminUpdateEventRequestDto;
import ru.practicum.ewmservice.dto.event.EventFullDto;
import ru.practicum.ewmservice.dto.user.NewUserRequest;
import ru.practicum.ewmservice.dto.user.UserDto;
import ru.practicum.ewmservice.service.adminewm.AdminServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    final AdminServiceImpl adminService;

    public AdminController(AdminServiceImpl adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/events")
    public List<EventFullDto> getEvents(@RequestParam List<Long> users,
                                        @RequestParam List<String> states,
                                        @RequestParam List<Long> categories,
                                        @RequestParam String rangeStart,
                                        @RequestParam String rangeEnd,
                                        @RequestParam int from,
                                        @RequestParam(defaultValue = "10") int size) {
        return adminService.getEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PutMapping("/envents/{eventId}")
    public EventFullDto putEvent(@PathVariable long eventId,
                                 @RequestBody AdminUpdateEventRequestDto requestDto) {
        return adminService.editEvent(eventId, requestDto);
    }

    @PatchMapping("/events/{eventId}/publish")
    public EventFullDto publishEvent(@PathVariable long eventId) {
        return adminService.publishEvent(eventId);
    }

    @PatchMapping("/events/{eventId}/reject")
    public EventFullDto rejectEvent(@PathVariable long eventId) {
        return adminService.rejectEvent(eventId);
    }

    @GetMapping("/categories/{catId}")
    public CategoryDto getCategory(@PathVariable long catId) {
        return adminService.getCategoryById(catId);
    }

    @PatchMapping("/categories")
    public CategoryDto patchCategory(@RequestBody CategoryDto categoryDto) {
        return adminService.updateCategory(categoryDto);
    }

    @PostMapping("/categories")
    public CategoryDto postCategory(@RequestBody NewCategoryDto newCategoryDto) {
        return adminService.createCategory(newCategoryDto);
    }

    @DeleteMapping("/categories/{catId}")
    public void deleteCategory(@PathVariable long catId) {
        adminService.deleteCategory(catId);
    }

    @GetMapping("/users")
    public List<UserDto> getUsers(@RequestParam List<Long> ids,
                                  @RequestParam int from,
                                  @RequestParam(defaultValue = "10") int size) {
        return adminService.getUsers(ids, from, size);
    }

    @PostMapping("/users")
    public UserDto postUser(@RequestBody NewUserRequest userRequest) {
        return adminService.createUser(userRequest);
    }

    @DeleteMapping("/users/{userId}")
    public void deleteUser(@PathVariable long userId) {
        adminService.deleteUser(userId);
    }

    @PostMapping("/compilations")
    public CompilationDto postCompilation(@RequestBody NewCompilationDto compilationDto) {
        return adminService.createCompilation(compilationDto);
    }

    @DeleteMapping("/compilations/{compId}")
    public void deleteCompilation(@PathVariable long compId) {
        adminService.deleteCompilation(compId);
    }

    @DeleteMapping("/compilations/{compId}/events/{eventId}")
    public void deleteEventFromCompilation(@PathVariable long compId,
                                           @PathVariable long eventId) {
        adminService.deleteEventFromCompilation(compId, eventId);
    }

    @PatchMapping("/compilations/{compId}/events/{eventId}")
    public void patchCompilation(@PathVariable long compId,
                                 @PathVariable long eventId) {
        adminService.addEventToCompilation(compId, eventId);
    }

    @DeleteMapping("/compilations/{compId}/pin")
    public void unpinCompilation(@PathVariable long compId) {
        adminService.unpinCompilation(compId);
    }

    @PatchMapping("/compilations/{compId}/pin")
    public void pinCompilation(@PathVariable long compId) {
        adminService.pinCompilation(compId);
    }
}