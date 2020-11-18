package io.github.batetolast1.spring.demo.service;

import io.github.batetolast1.spring.demo.dto.UserRegistrationDataDTO;

import javax.validation.Valid;

public interface RegistrationService {

    void register(@Valid UserRegistrationDataDTO userDTO);
}
