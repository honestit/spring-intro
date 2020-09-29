package io.github.batetolast1.spring.demo.controllers;

import io.github.batetolast1.spring.demo.dto.CreateAdvertDTO;
import io.github.batetolast1.spring.demo.model.domain.Advert;
import io.github.batetolast1.spring.demo.model.domain.User;
import io.github.batetolast1.spring.demo.model.repositories.AdvertRepository;
import io.github.batetolast1.spring.demo.model.repositories.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@Log4j2
public class AdvertController {

    private final UserRepository userRepository;
    private final AdvertRepository advertRepository;

    @Autowired
    public AdvertController(UserRepository userRepository, AdvertRepository advertRepository) {
        this.userRepository = userRepository;
        this.advertRepository = advertRepository;
    }

    @PostMapping("/add-advert")
    public String addAdvert(CreateAdvertDTO createAdvertDTO, Principal principal) {
        User loggedUser = userRepository.findByUsername(principal.getName());

        Advert advert = Advert.builder()
                .title(createAdvertDTO.getTitle())
                .description(createAdvertDTO.getDescription())
                .posted(LocalDateTime.now())
                .user(loggedUser)
                .build();
        log.info("Advert to add to DB: {}", advert);

        advertRepository.save(advert);
        log.info("Advert added to DB: {}", advert);

        return "redirect:/";
    }
}
