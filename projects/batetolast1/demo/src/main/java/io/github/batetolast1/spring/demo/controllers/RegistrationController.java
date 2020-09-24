package io.github.batetolast1.spring.demo.controllers;

import io.github.batetolast1.spring.demo.model.domain.User;
import io.github.batetolast1.spring.demo.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // tworzy komponent typu kontroler Spring MVC (odpowiednik servletu)
@RequestMapping("/register") // mapuje kontroler na wskazaną ścieżkę
public class RegistrationController {

    private final UserRepository userRepository;

    @Autowired // wstrzykujemy obiekt interfesju UserRepository do kontrolera (wzorzec Dependency Injection)
    public RegistrationController(UserRepository userRepository) { // korzystamy z zalecanej opcji wstrzyknięcia przez konstruktor
        this.userRepository = userRepository;
    }

    // inne dostępne opcje dla wstrzyknięć
    // użycie adnotacji @Autowired bezpośrednio nad polem reprezentującym obiekt, który chcemy wstrzyknąć (niezalecane)
    // użycie adnotacji @Autowired bezpośrednio nad metodą typu set dla pola, które chcemy wstrzyknąć

    @GetMapping // obsługuje żądanie typu GET
    public String prepareRegistrationPage() {
        return "/WEB-INF/views/registration-form.jsp"; // metoda ma zwracać identyfikator widoku (JSP)
    }

    @PostMapping // obsługuje żądanie typu POST
    public String processRegistrationPage(String username, // wartości są tworzone automatycznie na podstawie danych z formularza przesyłanych w żądaniu
                                          String password, // bardziej precyzyjne mapowania możemy osiągnąć, stosując adnotację @RequestParam przy każdym z parametrów
                                          String firstName, // np. gdy dany parametr ma być nieobowiązkowy albo gdy parametr żądania nazywa się inaczej niż parametr metody
                                          String lastName) {
        User user = User.builder()
                .username(username)
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .active(false)
                .build();

        user = userRepository.save(user);

        return "redirect:/index.html";
    }
}
