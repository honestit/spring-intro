package io.github.batetolast1.spring.demo.controllers;

import io.github.batetolast1.spring.demo.dto.CreateAdvertDTO;
import io.github.batetolast1.spring.demo.model.domain.Advert;
import io.github.batetolast1.spring.demo.model.repositories.AdvertRepository;
import io.github.batetolast1.spring.demo.model.repositories.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String addAdvert(CreateAdvertDTO createAdvertDTO) {

        Advert advert = Advert.builder()
                .title(createAdvertDTO.getTitle())
                .description(createAdvertDTO.getDescription())
                .posted(LocalDateTime.now())
                .user(userRepository.findByUsername(createAdvertDTO.getUsername()))
                .build();
        log.info("Advert to add: {}", advert);

        advertRepository.save(advert);
        log.info("Advert added to database: {}", advert);

        return "redirect:/";
    }
}
