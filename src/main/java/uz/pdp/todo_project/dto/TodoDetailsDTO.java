package uz.pdp.todo_project.dto;

import java.time.LocalDateTime;

public record TodoDetailsDTO(


        String title,
        String description,
        boolean completed,
        LocalDateTime createdAt


) {
}
