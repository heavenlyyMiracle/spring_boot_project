package uz.pdp.todo_project.handlers;

import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.pdp.todo_project.dto.ErrorDto;
import uz.pdp.todo_project.dto.ValidationErrorDto;
import uz.pdp.todo_project.exceptions.ItemNotFoundException;
import uz.pdp.todo_project.exceptions.NotOwnerException;
import uz.pdp.todo_project.exceptions.UsernameAlreadyTakenException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpRequest request) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach((fieldError) -> {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });

        String uri = request.getURI().toString();

        LocalDateTime timezone = LocalDateTime.now();
        int status = HttpStatus.BAD_REQUEST.value();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ValidationErrorDto(uri, status, errors, timezone));
    }

    @ExceptionHandler(UsernameAlreadyTakenException.class)
    public ResponseEntity<ErrorDto> handleUsernameAlreadyTakenException(UsernameAlreadyTakenException ex, HttpRequest request) {

        String message = ex.getMessage();
        LocalDateTime timezone = LocalDateTime.now();
        int status = HttpStatus.CONFLICT.value();
        String path = request.getURI().toString();

        ErrorDto error = new ErrorDto(message, path, status, timezone);

        return ResponseEntity.status(status).body(error);
    }


    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ErrorDto> handleItemNotFoundException(ItemNotFoundException ex, HttpRequest request) {

        String message = ex.getMessage();
        LocalDateTime timezone = LocalDateTime.now();
        int status = HttpStatus.NOT_FOUND.value();
        String path = request.getURI().toString();

        ErrorDto error = new ErrorDto(message, path, status, timezone);


        return ResponseEntity.status(status).body(error);


    }



    @ExceptionHandler(NotOwnerException.class)
    public ResponseEntity<ErrorDto> handleNotOwnerException(NotOwnerException ex, HttpRequest request) {

        String message = ex.getMessage();
        LocalDateTime timezone = LocalDateTime.now();
        int status = HttpStatus.FORBIDDEN.value();
        String path = request.getURI().toString();
        ErrorDto error = new ErrorDto(message, path, status, timezone);
        return ResponseEntity.status(status).body(error);


    }




}
