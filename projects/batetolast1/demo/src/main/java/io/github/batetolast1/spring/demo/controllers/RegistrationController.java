package io.github.batetolast1.spring.demo.controllers;

import io.github.batetolast1.spring.demo.dto.UserRegistrationDataDTO;
import io.github.batetolast1.spring.demo.service.impl.DefaultRegistrationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

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
    public String prepareRegistrationPage(Model model) {
        model.addAttribute("registrationData", new UserRegistrationDataDTO());
        return "registration-form";
    }

    @PostMapping
    public String processRegistrationPage(@ModelAttribute("registrationData") @Valid UserRegistrationDataDTO registrationData, BindingResult result) {
        if (result.hasErrors()) {
            return "registration-form";
        }
        try {
            registrationService.register(registrationData);
        } catch (ConstraintViolationException cve) {
            log.warn("Business constraints were violated for {}", registrationData);
            return "registration-form";
        }
        return "redirect:/login";
    }
}
