package io.github.batetolast1.spring.demo.service;

import io.github.batetolast1.spring.demo.dto.UserRegistrationDataDTO;

public interface RegistrationService {

    void register(UserRegistrationDataDTO userDTO);
}
