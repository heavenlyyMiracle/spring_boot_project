package uz.pdp.todo_project.dto;

import jakarta.validation.constraints.NotBlank;
import uz.pdp.todo_project.entity.Todo;

import java.io.Serializable;


public record TodoDto(

        @NotBlank(message = "Must not be blank") String title,
        @NotBlank(message = "Must not be blank") String description)
{
}