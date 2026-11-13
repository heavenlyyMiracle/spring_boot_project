package uz.pdp.todo_project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterDto(

        @NotBlank(message = "Must not be blank") String username,
        @Size(min = 8, message = "Weak password") @NotBlank(message = "Must not be blank") String password,
        @Email(message = "Invalid email") String email){
}