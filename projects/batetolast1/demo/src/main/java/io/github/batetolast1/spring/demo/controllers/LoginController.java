package io.github.batetolast1.spring.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    /*
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoginController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    */

    @GetMapping
    public String prepareLoginPage() {
        return "/WEB-INF/views/login-page.jsp";
    }

    /*
    // @PostMapping nie jest wymagane!
    @PostMapping
    public String processLoginPage(String username,
                                   String password) {
        User user = userRepository.findByUsername(username);

        if (user == null) throw new UsernameNotFoundException(username);

        if (passwordEncoder.matches(password, user.getPassword())) {
            return "redirect:/";
        } else {
            return "redirect:/login";
        }
    }
    */
}
