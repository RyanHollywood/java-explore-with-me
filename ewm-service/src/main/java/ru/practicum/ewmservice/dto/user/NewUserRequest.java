package ru.practicum.ewmservice.dto.user;

import lombok.Data;

@Data
public class NewUserRequest {
    private String email;
    private String name;
}
