package uz.pdp.todo_project.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record ValidationErrorDto(

        String path,
        int status,
        Map<String, String> fieldErrors,
        LocalDateTime timezone

) {
}
