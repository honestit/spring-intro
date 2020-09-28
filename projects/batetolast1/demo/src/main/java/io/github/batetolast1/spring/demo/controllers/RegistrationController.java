package io.github.batetolast1.spring.demo.controllers;

import io.github.batetolast1.spring.demo.model.domain.User;
import io.github.batetolast1.spring.demo.dto.RegisterUserDTO;
import io.github.batetolast1.spring.demo.model.repositories.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
@Log4j2
public class RegistrationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String prepareRegistrationPage() {
        return "registration-form";
    }

    @PostMapping // obsługuje żądanie typu POST
    public String processRegistrationPage(RegisterUserDTO registerUserDTO) {

        User user = User.builder()
                .username(registerUserDTO.getUsername())
                .password(passwordEncoder.encode(registerUserDTO.getPassword()))
                .firstName(registerUserDTO.getFirstName())
                .lastName(registerUserDTO.getLastName())
                .active(true)
                .build();
        log.info("User to register: {}", user);

        userRepository.save(user);
        log.info("Registered user: {}", user);
        return "redirect:/login";
    }
}
