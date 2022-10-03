package ru.practicum.ewmservice.service.adminewm;

import ru.practicum.ewmservice.dto.category.CategoryDto;
import ru.practicum.ewmservice.dto.category.NewCategoryDto;
import ru.practicum.ewmservice.dto.compilation.CompilationDto;
import ru.practicum.ewmservice.dto.compilation.NewCompilationDto;
import ru.practicum.ewmservice.dto.event.AdminUpdateEventRequestDto;
import ru.practicum.ewmservice.dto.event.EventFullDto;
import ru.practicum.ewmservice.dto.user.NewUserRequest;
import ru.practicum.ewmservice.dto.user.UserDto;

import java.util.List;

public interface AdminService {
    List<EventFullDto> getEvents();

    EventFullDto editEvent(long eventId, AdminUpdateEventRequestDto requestDto);

    EventFullDto publishEvent(long eventId);

    EventFullDto rejectEvent(long eventId);

    CategoryDto updateCategory(CategoryDto CategoryDto);

    CategoryDto createCategory(NewCategoryDto newCategoryDto);

    void deleteCategory(long catId);

    List<UserDto> getUsers();

    UserDto createUser(NewUserRequest userRequest);

    void deleteUser(long userId);

    CompilationDto createCompilation(NewCompilationDto compilationDto);

    void deleteCompilation(long compId);

    void deleteEventFromCompilation(long compId, long eventId);

    void addEventToCompilation(long compId, long eventId);

    void unpinCompilation(long compId);

    void pinCompilation(long compId);
}