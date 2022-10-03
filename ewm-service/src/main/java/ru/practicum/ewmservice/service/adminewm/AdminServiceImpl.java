package ru.practicum.ewmservice.service.adminewm;

import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.dto.category.CategoryDto;
import ru.practicum.ewmservice.dto.category.NewCategoryDto;
import ru.practicum.ewmservice.dto.compilation.CompilationDto;
import ru.practicum.ewmservice.dto.compilation.NewCompilationDto;
import ru.practicum.ewmservice.dto.event.AdminUpdateEventRequestDto;
import ru.practicum.ewmservice.dto.event.EventFullDto;
import ru.practicum.ewmservice.dto.user.NewUserRequest;
import ru.practicum.ewmservice.dto.user.UserDto;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Override
    public List<EventFullDto> getEvents() {
        return null;
    }

    @Override
    public EventFullDto editEvent(long eventId, AdminUpdateEventRequestDto requestDto) {
        return null;
    }

    @Override
    public EventFullDto publishEvent(long eventId) {
        return null;
    }

    @Override
    public EventFullDto rejectEvent(long eventId) {
        return null;
    }

    @Override
    public CategoryDto updateCategory(CategoryDto CategoryDto) {
        return null;
    }

    @Override
    public CategoryDto createCategory(NewCategoryDto newCategoryDto) {
        return null;
    }

    @Override
    public void deleteCategory(long catId) {
    }

    @Override
    public List<UserDto> getUsers() {
        return null;
    }

    @Override
    public UserDto createUser(NewUserRequest userRequest) {
        return null;
    }

    @Override
    public void deleteUser(long userId) {

    }

    @Override
    public CompilationDto createCompilation(NewCompilationDto compilationDto) {
        return null;
    }

    @Override
    public void deleteCompilation(long compId) {

    }

    @Override
    public void deleteEventFromCompilation(long compId, long eventId) {

    }

    @Override
    public void addEventToCompilation(long compId, long eventId) {

    }

    @Override
    public void unpinCompilation(long compId) {

    }

    @Override
    public void pinCompilation(long compId) {

    }
}
