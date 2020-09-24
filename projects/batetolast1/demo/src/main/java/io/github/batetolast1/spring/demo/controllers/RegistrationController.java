package io.github.batetolast1.spring.demo.controllers;

import io.github.batetolast1.spring.demo.model.domain.User;
import io.github.batetolast1.spring.demo.model.repositories.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // tworzy komponent typu kontroler Spring MVC (odpowiednik servletu)
@RequestMapping("/register") // mapuje kontroler na wskazaną ścieżkę
@Log4j2 // dodanie pola log z loggerem Log4j2 do klasy
public class RegistrationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired // wstrzykujemy obiekt interfejsu UserRepository do kontrolera (wzorzec Dependency Injection)
    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) { // korzystamy z zalecanej opcji wstrzyknięcia przez konstruktor
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder; // wstrzykujemy również obiekt klasy DelegatingPasswordEncoder, pozwalający na szyfrowanie hasła
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
                .password(passwordEncoder.encode(password))
                .firstName(firstName)
                .lastName(lastName)
                .active(true) // pozwalamy nowemu użytkownikowi od razu się zalogować
                .build();
        log.info("User to register: {}", user);

        userRepository.save(user);
        log.info("Registered user: {}", user);
        return "redirect:/index.html";
    }
}
