package pl.honestit.spring.demo.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.honestit.spring.demo.model.domain.Advert;
import pl.honestit.spring.demo.model.domain.User;
import pl.honestit.spring.demo.model.repositories.AdvertRepository;
import pl.honestit.spring.demo.model.repositories.UserRepository;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

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
    public String processAddAdvert(String title, String description, Principal principal, @RequestParam(defaultValue = "/") String redirectTo) {
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


        return "redirect:" + redirectTo;
    }

    @PostMapping("/delete-advert")
    public String processDeleteAdvert(Long advertId, Principal principal) {
        String username = principal.getName();
        log.debug("Usuwanie ogłoszenia o id {} dla użytkownika {}", advertId, username);

        Optional<Advert> optionalAdvert = advertRepository.findByIdAndOwnerUsername(advertId, username);
        optionalAdvert.ifPresent(advertRepository::delete);

        return "redirect:/user-adverts";
    }

    @GetMapping("/edit-advert")
    public String prepageEditAdvert(Long advertId, Principal principal, Model model) {
        String username = principal.getName();
        Optional<Advert> optionalAdvert = advertRepository.findByIdAndOwnerUsername(advertId, username);
        if (optionalAdvert.isPresent()) {
            model.addAttribute("advert", optionalAdvert.get());
            return "/WEB-INF/views/edit-advert-page.jsp";
        }
        else {
            return "redirect:/user-adverts";
        }
    }

    @PostMapping("/edit-advert")
    public String processEditAdvert(Long id, String title, String description, Principal principal) {
        String username = principal.getName();
        log.debug("Edycja ogłoszenia o id {} dla użytkownika {}", id, username);

        Optional<Advert> optionalAdvert = advertRepository.findByIdAndOwnerUsername(id, username);
        optionalAdvert.ifPresent(advert -> {
            advert.setTitle(title);
            advert.setDescription(description);
            advertRepository.save(advert);
        });

        return "redirect:/user-adverts";
    }
}
