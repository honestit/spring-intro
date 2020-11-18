package io.github.batetolast1.spring.demo.service.impl;

import io.github.batetolast1.spring.demo.model.repositories.UserRepository;
import io.github.batetolast1.spring.demo.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultValidationService implements ValidationService {

    private final UserRepository userRepository;

    @Autowired
    public DefaultValidationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isUniqueUsername(String username) {
        return !userRepository.existsByUsername(username);
    }
}
