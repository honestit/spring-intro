package io.github.batetolast1.spring.demo.controllers;

import io.github.batetolast1.spring.demo.model.domain.Advert;
import io.github.batetolast1.spring.demo.model.repositories.AdvertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomePageController {

    private final AdvertRepository advertRepository;

    @Autowired
    public HomePageController(AdvertRepository advertRepository) {
        this.advertRepository = advertRepository;
    }

    // Klasa Model reprezentuje idee modelu we wzorcu MVC, ale na poziomie relacji Widok <-> Kontroler.
    // Klasę tą należy rozumieć jako mapę, do której możemy wstawić dowolną wartość w kontrolerze i używać tej wartości w widoku.
    // Obiekt Model zastępuje nam tym samym mapę atrybutów żądania.
    // Kluczem w mapie jest obiekt typu String, a wartością obiekt dowolnego typu (mapa w postaci Map<String, Object>).
    // Każdy klucz, który umieścimy w modelu, będzie dostępny wewnątrz Expression Language (EL) tak, jak zmienna w kodzie.

    @GetMapping
    public String prepareHomePage(Model model) {
        List<Advert> adverts = advertRepository.findAllByOrderByPostedDesc();
        model.addAttribute("adverts", adverts);
        return "/WEB-INF/views/home-page.jsp";
    }
}
