package uz.pdp.todo_project.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.todo_project.config.jwt.JwtTokenUtil;
import uz.pdp.todo_project.dto.LoginDto;
import uz.pdp.todo_project.dto.RegisterDto;
import uz.pdp.todo_project.dto.UserListDTO;
import uz.pdp.todo_project.entity.User;
import uz.pdp.todo_project.enums.UserRole;
import uz.pdp.todo_project.exceptions.UsernameAlreadyTakenException;
import uz.pdp.todo_project.exceptions.UsernameNotFoundException;
import uz.pdp.todo_project.repo.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {


    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, AuthenticationManager authenticationManager, AuthenticationManager authenticationManager1, JwtTokenUtil jwtTokenUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager1;
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(LoginDto loginDto) {

        Optional<User> optional = userRepository.findByUsername(loginDto.username());
        User user = optional.orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), loginDto.password());

        authenticationManager.authenticate(authenticationToken);


        return jwtTokenUtil.generateToken(user);
    }


    public void register(RegisterDto registerDto) {

        Optional<User> byUsername = userRepository.findByUsername(registerDto.username());

        Optional<User> byEmail = userRepository.findByEmail(registerDto.email());


        if (byUsername.isPresent() || byEmail.isPresent()) {
            throw new UsernameAlreadyTakenException("Username or email is already taken");
        }


        User user = User.builder()
                .username(registerDto.username())
                .email(registerDto.email())
                .password(passwordEncoder.encode(registerDto.password()))
                .email(registerDto.email())
                .role(UserRole.ROLE_USER)
                .build();

        userRepository.save(user);

    }


    public List<UserListDTO> getAllUsers() {

        List<User> all = userRepository.findAll();

        return all.stream().map( this::toDTO ).toList();
    }



    public UserListDTO toDTO(User user) {

        return new UserListDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole());

    }


}
