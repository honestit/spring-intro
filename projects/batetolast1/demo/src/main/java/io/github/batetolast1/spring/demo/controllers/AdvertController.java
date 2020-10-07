package io.github.batetolast1.spring.demo.controllers;

import io.github.batetolast1.spring.demo.dto.CreateAdvertDTO;
import io.github.batetolast1.spring.demo.service.impl.DefaultAdvertService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Log4j2
public class AdvertController {

    private final DefaultAdvertService defaultAdvertService;

    @Autowired
    public AdvertController(DefaultAdvertService defaultAdvertService) {
        this.defaultAdvertService = defaultAdvertService;
    }

    @PostMapping("/add-advert")
    public String addAdvert(CreateAdvertDTO createAdvertDTO) {
        defaultAdvertService.addAdvert(createAdvertDTO);
        return "redirect:/";
    }
}
