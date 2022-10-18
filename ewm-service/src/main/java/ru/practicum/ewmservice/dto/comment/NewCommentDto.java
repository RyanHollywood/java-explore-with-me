package ru.practicum.ewmservice.dto.comment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class NewCommentDto {
    private long author_id;
    private long eventId;
    private String text;
}
