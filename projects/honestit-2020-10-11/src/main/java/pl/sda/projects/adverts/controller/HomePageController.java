package pl.sda.projects.adverts.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sda.projects.adverts.model.domain.Advert;
import pl.sda.projects.adverts.model.repository.AdvertRepository;

@Controller
@RequestMapping("/")
@Slf4j @RequiredArgsConstructor
public class HomePageController {

    private final AdvertRepository advertRepository;

    @GetMapping
    public String prepareHomePage(Model model) {
        model.addAttribute("advertToAdd", Advert.builder().build());
        model.addAttribute("adverts", advertRepository.findAllByOrderByPostedDesc());
        return "home";
    }


}
