package pl.sda.projects.adverts.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.sda.projects.adverts.model.domain.User;
import pl.sda.projects.adverts.model.repository.UserRepository;

@Controller @Slf4j
@RequestMapping("/register")
public class RegistrationController {

    private UserRepository userRepository;

    @Autowired // userRepostiory będzie wstrzyknięte teraz przez Springa, hahahaha!!!
    public RegistrationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String prepareRegistrationPage(Model model) {
        model.addAttribute("greetings", "Hello, Spring!");
        return "registration/registration-form";
    }

    @PostMapping
    public String processRegistrationData(@RequestParam String username,
                                          @RequestParam String password,
                                          @RequestParam String firstName,
                                          @RequestParam String lastName,
                                          ModelMap model) {
        User user = User.builder()
                .username(username)
                .password(password)
                .firstName(firstName)
                .lastName(lastName).build();

        log.debug("User to save: {}", user);
        userRepository.save(user);
        log.info("New user saved: {}", user);

        model.addAttribute("savedUser", user);
        return "registration/registration-summary";
    }

}
