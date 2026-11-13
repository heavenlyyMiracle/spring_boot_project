package uz.pdp.todo_project.dto;

import java.time.LocalDateTime;


public record ErrorDto(
        String message,
        String path,
        int status,
        LocalDateTime timezone
) {
}
