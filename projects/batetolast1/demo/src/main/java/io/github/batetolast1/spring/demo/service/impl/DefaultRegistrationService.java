package io.github.batetolast1.spring.demo.service.impl;

import io.github.batetolast1.spring.demo.dto.UserRegistrationDataDTO;
import io.github.batetolast1.spring.demo.model.domain.Role;
import io.github.batetolast1.spring.demo.model.domain.User;
import io.github.batetolast1.spring.demo.model.repositories.RoleRepository;
import io.github.batetolast1.spring.demo.model.repositories.UserRepository;
import io.github.batetolast1.spring.demo.service.RegistrationService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Service
@Validated
@Log4j2
public class DefaultRegistrationService implements RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public DefaultRegistrationService(UserRepository userRepository,
                                      PasswordEncoder passwordEncoder,
                                      RoleRepository roleRepository,
                                      ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Validated
    public void register(@Valid UserRegistrationDataDTO registrationData) {
        log.debug("Registration data to create user: {}", registrationData);

        User user = modelMapper.map(registrationData, User.class);
        log.debug("User after mapping from registrationData: {}", user);

        Role roleUser = roleRepository.getByName("ROLE_USER");
        user.getRoles().add(roleUser);
        user.setActive(Boolean.TRUE);
        user.setPassword(passwordEncoder.encode(registrationData.getPassword()));
        log.debug("User before save: {}", user);

        userRepository.save(user);
        log.debug("User after save: {}", user);
    }
}
