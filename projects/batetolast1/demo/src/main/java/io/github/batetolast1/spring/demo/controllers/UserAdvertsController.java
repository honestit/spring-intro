package io.github.batetolast1.spring.demo.controllers;

import io.github.batetolast1.spring.demo.dto.*;
import io.github.batetolast1.spring.demo.service.impl.DefaultAdvertService;
import io.github.batetolast1.spring.demo.service.impl.DefaultUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Log4j2
public class UserAdvertsController {

    private final DefaultAdvertService defaultAdvertService;
    private final DefaultUserService defaultUserService;

    @Autowired
    public UserAdvertsController(DefaultAdvertService defaultAdvertService, DefaultUserService defaultUserService) {
        this.defaultAdvertService = defaultAdvertService;
        this.defaultUserService = defaultUserService;
    }

    @GetMapping(value = {"/user-adverts", "/user-adverts/{id:\\d+}"})
    public String getUserAdverts(@PathVariable(required = false) Long id, Model model) {
        ShowUserDTO advertsOwnerDTO = defaultAdvertService.getAdvertsOwnerUsername(id);
        if (advertsOwnerDTO == null) {
            return "redirect:/";
        }
        model.addAttribute("advertsOwnerDTO", advertsOwnerDTO);

        List<ShowAdvertDTO> ownerAdvertDTOs = defaultAdvertService.getUserAdverts(id);
        model.addAttribute("ownerAdvertDTOs", ownerAdvertDTOs);

        return "user-adverts-page";
    }

    @PostMapping("/delete-advert")
    public String deleteAdvert(DeleteAdvertDTO deleteAdvertDTO) {
        defaultUserService.deleteAdvert(deleteAdvertDTO);
        return "redirect:/user-adverts";
    }

    @GetMapping("/edit-advert")
    public String editAdvert(Long advertId, Model model) {
        EditAdvertDTO editAdvertDTO = defaultAdvertService.getEditedAdvert(advertId);
        model.addAttribute("editAdvertDTO", editAdvertDTO);
        return editAdvertDTO == null ? "redirect:/user-adverts" : "edit-advert-form";
    }

    @PostMapping("/edit-advert")
    public String editAdvert(EditAdvertDTO editAdvertDTO) {
        defaultUserService.editAdvert(editAdvertDTO);
        return "redirect:/user-adverts";
    }

    @GetMapping("/observed-adverts")
    public String getObservedAdverts(Model model) {
        List<ShowAdvertDTO> observedAdvertDTOs = defaultAdvertService.getObservedAdverts();
        model.addAttribute("observedAdvertDTOs", observedAdvertDTOs);
        return "observed-adverts-page";
    }

    @PostMapping("/observe-advert")
    public String observeAdvert(ObserveAdvertDTO observeAdvertDTO) {
        defaultUserService.observeAdvert(observeAdvertDTO);
        return "redirect:/observed-adverts";
    }

    @PostMapping("/unobserve-advert")
    public String unobserveAdvert(UnobserveAdvertDTO unobserveAdvertDTO) {
        defaultUserService.unobserveAdvert(unobserveAdvertDTO);
        return "redirect:/observed-adverts";
    }
}
