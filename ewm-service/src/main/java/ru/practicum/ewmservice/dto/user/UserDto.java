package ru.practicum.ewmservice.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private long id;
    private String name;
    private String email;
}
