package io.github.batetolast1.spring.demo.controllers;

import io.github.batetolast1.spring.demo.model.domain.Advert;
import io.github.batetolast1.spring.demo.model.domain.User;
import io.github.batetolast1.spring.demo.model.repositories.AdvertRepository;
import io.github.batetolast1.spring.demo.model.repositories.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/")
@Log4j2
public class HomePageController {

    private final UserRepository userRepository;
    private final AdvertRepository advertRepository;

    @Autowired
    public HomePageController(UserRepository userRepository, AdvertRepository advertRepository) {
        this.userRepository = userRepository;
        this.advertRepository = advertRepository;
    }

    @GetMapping
    public String prepareHomePage(Principal principal, Model model) {
        User loggedUser = (principal != null) ? userRepository.findByUsername(principal.getName()) : null;
        model.addAttribute("loggedUser", loggedUser);
        log.info("Logged user={}", loggedUser);

        List<Advert> adverts = (principal != null) ? advertRepository.findAllByOrderByPostedDesc() : advertRepository.findFirst10ByOrderByPostedDesc();
        model.addAttribute("adverts", adverts);
        log.info("All adverts={}", adverts);

        return "home-page";
    }
}
