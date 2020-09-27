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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@Log4j2
public class UserAdvertsController {

    private final UserRepository userRepository;
    private final AdvertRepository advertRepository;

    @Autowired
    public UserAdvertsController(UserRepository userRepository, AdvertRepository advertRepository) {
        this.userRepository = userRepository;
        this.advertRepository = advertRepository;
    }

    @GetMapping("/user-adverts")
    public String getUserAdverts(Principal principal, Model model) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username);
        log.info("User: {}", user);
        List<Advert> userAdverts = advertRepository.findAllByUserOrderByPostedDesc(user);
        model.addAttribute("userAdverts", userAdverts);
        model.addAttribute("userAvertsUsername", username);
        return "/WEB-INF/views/user-adverts-page.jsp";
    }

    @GetMapping("/user-adverts/{id:\\d+}")
    public String getUserAdverts(@PathVariable Long id, Model model) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<Advert> userAdverts = advertRepository.findAllByUserOrderByPostedDesc(user);
            model.addAttribute("userAdverts", userAdverts);
            model.addAttribute("userAvertsUsername", user.getUsername());
            return "/WEB-INF/views/user-adverts-page.jsp";
        } else {
            return "redirect:/"; // TODO redirect to 404
        }
    }

    @PostMapping("/delete-advert")
    public String deleteAdvert(Long advertId, Principal principal) {
        Optional<Advert> optionalAdvert = advertRepository.findById(advertId);
        if (optionalAdvert.isPresent()) {
            Advert advert = optionalAdvert.get();
            String username = principal.getName();
            User user = userRepository.findByUsername(username);
            if (user.getId().equals(advert.getUser().getId())) {
                advertRepository.delete(advert);
            }
        }
        return "redirect:/user-adverts";
    }

    @GetMapping("/edit-advert")
    public String editAdvert(Long advertId, Principal principal, Model model) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username);
        Advert advert = advertRepository.getOne(advertId);

        if (advert.getUser().getId().equals(user.getId())) {
            model.addAttribute("editedAdvert", advert);
            return "/WEB-INF/views/edit-advert-form.jsp";
        }
        return "redirect:/user-adverts";
    }

    @PostMapping("/edit-advert")
    public String editAdvert(Long advertId, String title, String description, Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username);
        Advert advert = advertRepository.getOne(advertId);

        if (advert.getUser().getId().equals(user.getId())) {
            advert.setTitle(title);
            advert.setDescription(description);
            advertRepository.save(advert);
        }
        return "redirect:/user-adverts";
    }

    @GetMapping("/observed-adverts")
    public String getObservedAdverts(Principal principal, Model model) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username);
        model.addAttribute("observedAdverts", user.getObservedAdverts());
        return "/WEB-INF/views/observed-adverts-page.jsp";
    }
}
