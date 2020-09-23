package io.github.batetolast1.spring.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // tworzy komponent typu kontroler Spring MVC (odpowiednik servletu)
@RequestMapping("/register") // mapuje kontroler na wskazaną ścieżkę
public class RegistrationController {

    @GetMapping // obsługuje żądanie typu GET
    public String prepareRegistrationPage() {
        return "/WEB-INF/views/registration-form.jsp"; // metoda ma zwracać identyfikator widoku (JSP)
    }

    @PostMapping // obsługuje żądanie typu POST
    public String processRegistrationPage() {
        // TODO
        return "";
    }
}
