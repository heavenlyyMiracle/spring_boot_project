package uz.pdp.todo_project.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.todo_project.dto.UserListDTO;
import uz.pdp.todo_project.service.AuthService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {


    private final AuthService authService;

    public AdminController(AuthService authService) {
        this.authService = authService;
    }



    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    public ResponseEntity<List<UserListDTO>> todoList() {

        List<UserListDTO> users = authService.getAllUsers();

        return ResponseEntity.ok(users);
    }



}
