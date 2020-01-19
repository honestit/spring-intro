package pl.honestit.spring.demo.controllers;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String prepareLoginPage() {
        return "/WEB-INF/views/login-page.jsp";
    }
}
