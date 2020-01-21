package pl.honestit.spring.demo.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.honestit.spring.demo.model.domain.Advert;
import pl.honestit.spring.demo.model.repositories.AdvertRepository;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/")
@Slf4j
public class HomePageController {

    private final AdvertRepository advertRepository;

    @Autowired
    public HomePageController(AdvertRepository advertRepository) {
        this.advertRepository = advertRepository;
    }

    @GetMapping
    public String prepareHomePage(Model model, Principal principal) {
        List<Advert> adverts;
        if (principal != null) {
            adverts = advertRepository.findAllByOrderByPostedDesc();
            log.debug("Pełna lista ogłoszeń: {}", adverts);
        }
        else {
            adverts = advertRepository.findFirst10ByOrderByPostedDesc();
            log.debug("Ograniczona lista ogłoszeń: {}", adverts);
        }
        model.addAttribute("adverts", adverts);
        return "/WEB-INF/views/home-page.jsp";
    }
}
