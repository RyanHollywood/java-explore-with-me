package ru.practicum.ewmservice.mapper;

import ru.practicum.ewmservice.dto.comment.CommentDto;
import ru.practicum.ewmservice.model.Comment;

import java.time.format.DateTimeFormatter;

public class CommentMapper {

    public static CommentDto toCommentDto(Comment comment, String pattern) {
        return CommentDto.builder()
                .id(comment.getId())
                .eventId(comment.getEvent().getId())
                .author(UserMapper.toUserShortDto(comment.getAuthor()))
                .text(comment.getText())
                .created(comment.getCreated().format(DateTimeFormatter.ofPattern(pattern)))
                .build();
    }
}