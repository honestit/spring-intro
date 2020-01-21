package pl.honestit.spring.demo.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import pl.honestit.spring.demo.model.domain.Advert;
import pl.honestit.spring.demo.model.domain.User;
import pl.honestit.spring.demo.model.repositories.AdvertRepository;
import pl.honestit.spring.demo.model.repositories.UserRepository;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@Slf4j
public class AdvertController {

    private final UserRepository userRepository;
    private final AdvertRepository advertRepository;

    @Autowired
    public AdvertController(UserRepository userRepository, AdvertRepository advertRepository) {
        this.userRepository = userRepository;
        this.advertRepository = advertRepository;
    }

    @PostMapping("/add-advert")
    public String processAddAdvert(String title, String description, Principal principal) {
        String username = principal.getName();
        log.debug("Dodawanie ogłoszenia dla zalogowanego użytkownika: {}", username);

        User user = userRepository.getByUsername(username);
        log.debug("Zalogowany użytkownik: {}", user);

        Advert advert = Advert.builder()
                .title(title)
                .description(description)
                .owner(user)
                .posted(LocalDateTime.now())
                .build();

        log.debug("Ogłoszenie do dodania: {}", advert);
        advertRepository.save(advert);
        log.debug("Zapisane ogłoszenie: {}", advert);

        return "redirect:/";
    }
}
