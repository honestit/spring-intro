package io.github.batetolast1.spring.demo.controllers;

import io.github.batetolast1.spring.demo.dto.RegisterUserDTO;
import io.github.batetolast1.spring.demo.service.impl.DefaultRegistrationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
@Log4j2
public class RegistrationController {

    private final DefaultRegistrationService registrationService;

    @Autowired
    public RegistrationController(DefaultRegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping
    public String prepareRegistrationPage() {
        return "registration-form";
    }

    @PostMapping
    public String processRegistrationPage(RegisterUserDTO registerUserDTO) {
        registrationService.register(registerUserDTO);
        return "redirect:/login";
    }
}
