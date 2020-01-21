package pl.honestit.spring.demo.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.honestit.spring.demo.model.domain.Advert;
import pl.honestit.spring.demo.model.repositories.AdvertRepository;
import pl.honestit.spring.demo.model.repositories.UserRepository;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user-adverts")
@Slf4j
public class UserAdvertsController {

    private final UserRepository userRepository;
    private final AdvertRepository advertRepository;

    @Autowired
    public UserAdvertsController(UserRepository userRepository, AdvertRepository advertRepository) {
        this.userRepository = userRepository;
        this.advertRepository = advertRepository;
    }

    @GetMapping
    public String prepareLoggedUserAdvertsPage(Model model, Principal principal) {
        String username = principal.getName();
        List<Advert> userAdverts = advertRepository.findAllByOwnerUsernameOrderByPostedDesc(username);

        model.addAttribute("adverts", userAdverts);
        return "/WEB-INF/views/user-adverts-page.jsp";
    }
}
