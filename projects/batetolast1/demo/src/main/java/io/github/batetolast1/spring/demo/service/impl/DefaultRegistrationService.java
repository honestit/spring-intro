package io.github.batetolast1.spring.demo.service.impl;

import io.github.batetolast1.spring.demo.dto.RegisterUserDTO;
import io.github.batetolast1.spring.demo.model.domain.Role;
import io.github.batetolast1.spring.demo.model.domain.User;
import io.github.batetolast1.spring.demo.model.repositories.RoleRepository;
import io.github.batetolast1.spring.demo.model.repositories.UserRepository;
import io.github.batetolast1.spring.demo.service.RegistrationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class DefaultRegistrationService implements RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public DefaultRegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public void register(RegisterUserDTO userDTO) {
        Role roleUser = roleRepository.getByName("ROLE_USER");
        log.info("ROLE_USER: {}", roleUser);

        User user = User.builder()
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .active(true)
                .role(roleUser)
                .build();
        log.info("User to register: {}", user);


        userRepository.save(user);
        log.info("Registered user: {}", user);
    }
}
