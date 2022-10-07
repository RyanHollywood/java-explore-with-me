package ru.practicum.ewmservice.service.adminewm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.dto.category.CategoryDto;
import ru.practicum.ewmservice.dto.category.NewCategoryDto;
import ru.practicum.ewmservice.dto.compilation.CompilationDto;
import ru.practicum.ewmservice.dto.compilation.NewCompilationDto;
import ru.practicum.ewmservice.dto.event.AdminUpdateEventRequestDto;
import ru.practicum.ewmservice.dto.event.EventFullDto;
import ru.practicum.ewmservice.dto.user.NewUserRequest;
import ru.practicum.ewmservice.dto.user.UserDto;
import ru.practicum.ewmservice.mapper.CategoryMapper;
import ru.practicum.ewmservice.mapper.UserMapper;
import ru.practicum.ewmservice.model.Category;
import ru.practicum.ewmservice.model.Compilation;
import ru.practicum.ewmservice.model.User;
import ru.practicum.ewmservice.storage.CategoryRepository;
import ru.practicum.ewmservice.storage.CompilationRepository;
import ru.practicum.ewmservice.storage.UserRepository;

import java.util.List;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final CompilationRepository compilationRepository;

    @Autowired
    public AdminServiceImpl(CategoryRepository categoryRepository, UserRepository userRepository, CompilationRepository compilationRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.compilationRepository = compilationRepository;
    }

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
        Category newCategory = CategoryMapper.fromNewCategoryDto(newCategoryDto);
        newCategory = categoryRepository.save(newCategory);
        log.debug("New category {} with id:{} created", newCategory.getName(), newCategory.getId());
        return CategoryMapper.toCategoryDto(newCategory);
    }

    @Override
    public void deleteCategory(long catId) {
        categoryRepository.deleteById(catId);
    }

    @Override
    public List<UserDto> getUsers() {
        return null;
    }

    @Override
    public UserDto createUser(NewUserRequest userRequest) {
        User newUser = UserMapper.fromNewUserRequest(userRequest);
        newUser = userRepository.save(newUser);
        log.debug("New user {} created", newUser.getName());
        return UserMapper.toUserDto(newUser);
    }

    @Override
    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
        log.debug("User id:{} deleted", userId);
    }

    @Override
    public CompilationDto createCompilation(NewCompilationDto compilationDto) {
        return null;
    }

    @Override
    public void deleteCompilation(long compId) {
        compilationRepository.deleteById(compId);
        log.debug("Compilation id:{} deleted", compId);
    }

    @Override
    public void deleteEventFromCompilation(long compId, long eventId) {

    }

    @Override
    public void addEventToCompilation(long compId, long eventId) {

    }

    @Override
    public void unpinCompilation(long compId) {
        Compilation compToUnpin = compilationRepository.findById(compId).orElseThrow();
        compToUnpin.setPinned(false);
        compilationRepository.save(compToUnpin);
    }

    @Override
    public void pinCompilation(long compId) {
        Compilation compToPin = compilationRepository.findById(compId).orElseThrow();
        compToPin.setPinned(true);
        compilationRepository.save(compToPin);
    }
}
