package uz.pdp.todo_project.service;

import org.springframework.stereotype.Service;
import uz.pdp.todo_project.dto.TodoDetailsDTO;
import uz.pdp.todo_project.dto.TodoDto;
import uz.pdp.todo_project.entity.Todo;
import uz.pdp.todo_project.exceptions.ItemNotFoundException;
import uz.pdp.todo_project.exceptions.NotOwnerException;
import uz.pdp.todo_project.repo.TodoRepository;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class TodoService {


    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public void create(TodoDto todoDto) {


        Todo todo = Todo.builder()
                .title(todoDto.title())
                .description(todoDto.description())
                .createdAt(LocalDateTime.now())
                .completed(false)
                .build();


        todoRepository.save(todo);

    }


    public void delete(Long todoId, Principal principal) {


        Optional<Todo> byId = todoRepository.findById(todoId);

        Todo todo = byId.orElseThrow(() -> new ItemNotFoundException("Todo with id: " + todoId + " not found"));


        if (!isOwner(todo, principal)) {
            throw new NotOwnerException("Not owner");
        }

        todoRepository.deleteById(todoId);

    }




    public TodoDetailsDTO todoById(Long todoId, Principal principal) {

        Optional<Todo> byId = todoRepository.findById(todoId);

        Todo todo = byId.orElseThrow(() -> new ItemNotFoundException("Todo with id: " + todoId + " not found"));


        if (isOwner(todo, principal)) {

            throw new NotOwnerException("You are not owner of this TODO");

        }




        return toDTO(todo);
    }


    public TodoDetailsDTO toDTO(Todo todo) {
        return new TodoDetailsDTO(todo.getTitle(), todo.getDescription(), todo.isCompleted(), todo.getCreatedAt());
    }


    public boolean isOwner(Todo todo,  Principal principal) {

        Long id = Long.parseLong(principal.getName());
        Long ownerId = todo.getUserId().getId();


        return id.equals(ownerId);
    }




}
