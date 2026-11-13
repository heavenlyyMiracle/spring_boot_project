package uz.pdp.todo_project.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.todo_project.enums.UserRole;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String password;
    private String email;
    private UserRole role;


    @OneToMany(cascade = CascadeType.ALL)
    List<Todo> todos;



}
