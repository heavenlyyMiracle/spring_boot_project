package uz.pdp.todo_project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginDto(

        @NotBlank(message = "Must not be blank")
        String username,

        @NotBlank(message = "Must not be blank")
        @Size(min = 8, message = "Invalid password")
        String password
) {
}
