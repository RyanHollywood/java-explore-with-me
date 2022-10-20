package ru.practicum.ewmservice.dto.comment;

import lombok.Builder;
import lombok.Data;
import ru.practicum.ewmservice.dto.user.UserShortDto;

@Data
@Builder
public class CommentDto {
    private long id;
    private long eventId;
    private UserShortDto author;
    private String text;
    private String created;
}
