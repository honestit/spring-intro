package io.github.batetolast1.spring.demo.controllers;

import io.github.batetolast1.spring.demo.dto.EditAdvertDTO;
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
import java.util.Set;

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

    @GetMapping(value = {"/user-adverts", "/user-adverts/{id:\\d+}"})
    public String getUserAdverts(@PathVariable(required = false) Long id, Principal principal, Model model) {
        String username = principal.getName();
        User loggedUser = userRepository.findByUsername(username);
        model.addAttribute("loggedUser", loggedUser);
        log.info("Logged user={}", loggedUser);

        User advertsOwner;
        if (id == null) {
            advertsOwner = loggedUser;
        } else {
            Optional<User> optionalUser = userRepository.findById(id);
            if (optionalUser.isEmpty()) {
                log.info("Adverts' owner not found, id={}", id);
                return "redirect:/";
            }
            advertsOwner = optionalUser.get();
        }
        model.addAttribute("advertsOwner", advertsOwner);
        log.info("Adverts' owner={}", advertsOwner);

        List<Advert> ownerAdverts = advertRepository.findAllByUserOrderByPostedDesc(advertsOwner);
        model.addAttribute("ownerAdverts", ownerAdverts);
        log.info("Owner's adverts={}", ownerAdverts);

        return "user-adverts-page";
    }

    @PostMapping("/delete-advert")
    public String deleteAdvert(Long advertId, Principal principal) {
        Optional<Advert> optionalAdvert = advertRepository.findById(advertId);

        if (optionalAdvert.isPresent()) {
            Advert advert = optionalAdvert.get();
            log.info("Advert to delete={}", advert);

            String username = principal.getName();
            User loggedUser = userRepository.findByUsername(username);
            log.info("Logged user={}", loggedUser);

            if (loggedUser == advert.getUser()) {
                advertRepository.delete(advert);
                log.info("Advert deleted!");
            } else {
                log.info("Advert wasn't created by logged user, deleting failed!");
            }
        } else {
            log.info("Advert not found, id={}", advertId);
        }
        return "redirect:/user-adverts";
    }

    @GetMapping("/edit-advert")
    public String editAdvert(Long advertId, Principal principal, Model model) {
        String username = principal.getName();
        User loggedUser = userRepository.findByUsername(username);
        log.info("Logged user={}", loggedUser);

        Advert advert = advertRepository.getOne(advertId);
        log.info("Advert to edit={}", advert);

        if (loggedUser != advert.getUser()) {
            log.info("Advert wasn't created by logged user, can't edit!");
            return "redirect:/user-adverts";
        }

        model.addAttribute("editedAdvert", advert);
        return "edit-advert-form";
    }

    @PostMapping("/edit-advert")
    public String editAdvert(EditAdvertDTO editAdvertDTO) {
        User loggedUser = userRepository.findByUsername(editAdvertDTO.getUsername());
        log.info("Logged user={}", loggedUser);

        Advert advert = advertRepository.getOne(editAdvertDTO.getAdvertId());
        log.info("Advert to edit={}", advert);

        if (loggedUser == advert.getUser()) {
            advert.setTitle(editAdvertDTO.getTitle());
            advert.setDescription(editAdvertDTO.getDescription());
            log.info("Advert to update={}", advert);

            advertRepository.save(advert);
            log.info("Updated advert={}", advert);
        } else {
            log.info("Advert wasn't created by logged user, can't edit!");
        }
        return "redirect:/user-adverts";
    }

    @GetMapping("/observed-adverts")
    public String getObservedAdverts(Principal principal, Model model) {
        String username = principal.getName();
        User loggedUser = userRepository.findByUsername(username);
        log.info("Logged user={}", loggedUser);

        Set<Advert> observedAdverts = loggedUser.getObservedAdverts();
        model.addAttribute("observedAdverts", observedAdverts);
        log.info("Observed adverts={}", observedAdverts);
        return "observed-adverts-page";
    }

    @PostMapping("/observe-advert")
    public String observeAdvert(Principal principal, Long advertId) {
        String username = principal.getName();
        User loggedUser = userRepository.findByUsername(username);
        log.info("Logged user={}", loggedUser);

        Advert advert = advertRepository.getOne(advertId);
        log.info("Advert to observe={}", advert);

        if (advert.getUser() != loggedUser) {
            loggedUser.getObservedAdverts().add(advert);
            userRepository.save(loggedUser);
            log.info("Advert observed!");
        } else {
            log.info("Advert was created by logged user, can't be observed");
        }
        return "redirect:/observed-adverts";
    }

    @PostMapping("/unobserve-advert")
    public String unobserveAdvert(Principal principal, Long advertId) {
        String username = principal.getName();
        User loggedUser = userRepository.findByUsername(username);
        log.info("Logged user={}", loggedUser);

        Advert advert = advertRepository.getOne(advertId);
        log.info("Advert to unobserve={}", advert);

        if (loggedUser.getObservedAdverts().remove(advert)) {
            userRepository.save(loggedUser);
            log.info("Advert unobserved!");
        } else {
            log.info("Advert was not observed, thus can't be unobserved");
        }

        return "redirect:/observed-adverts";
    }
}
