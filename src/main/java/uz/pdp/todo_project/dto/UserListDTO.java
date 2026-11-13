package uz.pdp.todo_project.dto;

import uz.pdp.todo_project.enums.UserRole;

public record UserListDTO(

        Long id,
        String username,
        String email,
        UserRole role


) {
}
