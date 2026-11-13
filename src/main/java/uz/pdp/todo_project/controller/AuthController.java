package uz.pdp.todo_project.controller;

import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.todo_project.dto.LoginDto;
import uz.pdp.todo_project.dto.RegisterDto;
import uz.pdp.todo_project.service.AuthService;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {


    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register( @Valid @RequestBody RegisterDto dto) {


        authService.register(dto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto) {

        String login = authService.login(loginDto);
        return ResponseEntity.ok(login);
    }









}
