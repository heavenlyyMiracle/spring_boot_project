package uz.pdp.todo_project.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.todo_project.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}