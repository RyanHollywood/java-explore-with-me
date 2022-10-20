package ru.practicum.ewmservice.service.adminewm;

import ru.practicum.ewmservice.dto.category.CategoryDto;
import ru.practicum.ewmservice.dto.category.NewCategoryDto;
import ru.practicum.ewmservice.dto.comment.CommentDto;
import ru.practicum.ewmservice.dto.compilation.CompilationDto;
import ru.practicum.ewmservice.dto.compilation.NewCompilationDto;
import ru.practicum.ewmservice.dto.event.AdminUpdateEventRequestDto;
import ru.practicum.ewmservice.dto.event.EventFullDto;
import ru.practicum.ewmservice.dto.user.NewUserRequest;
import ru.practicum.ewmservice.dto.user.UserDto;

import java.util.List;

public interface AdminService {
    List<EventFullDto> getEvents(List<Long> users, List<String> states, List<Long> categories, String rangeStart,
                                 String rangeEnd, int from, int size);

    EventFullDto editEvent(long eventId, AdminUpdateEventRequestDto requestDto);

    EventFullDto publishEvent(long eventId);

    EventFullDto rejectEvent(long eventId);

    CategoryDto getCategoryById(long catId);

    CategoryDto updateCategory(CategoryDto categoryDto);

    CategoryDto createCategory(NewCategoryDto newCategoryDto);

    void deleteCategory(long catId);

    List<UserDto> getUsers(List<Long> ids, int from, int size);

    UserDto createUser(NewUserRequest userRequest);

    void deleteUser(long userId);

    CompilationDto createCompilation(NewCompilationDto compilationDto);

    void deleteCompilation(long compId);

    void deleteEventFromCompilation(long compId, long eventId);

    void addEventToCompilation(long compId, long eventId);

    void unpinCompilation(long compId);

    void pinCompilation(long compId);

    List<CommentDto> getComments(long eventId);

    List<CommentDto> getUserComments(long userId);

    CommentDto getComment(long eventId, long comId);

    void deleteComment(long eventId, long comId);
}