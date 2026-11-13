package uz.pdp.todo_project.controller;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.todo_project.dto.TodoDetailsDTO;
import uz.pdp.todo_project.dto.TodoDto;
import uz.pdp.todo_project.service.TodoService;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
public class TodoController {


    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }





    @PostMapping("/create-todo")
    public ResponseEntity<Void>  createTodo(@Valid @RequestBody TodoDto todoDto) {

        todoService.create(todoDto);

        return ResponseEntity.ok().build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<TodoDetailsDTO> todoById(@PathVariable Long id, Principal principal) {


        TodoDetailsDTO todoDto = todoService.todoById(id, principal);


        return ResponseEntity.ok(todoDto);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTodoById(@PathVariable Long id, Principal principal) {

        todoService.delete(id, principal);

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }








}
