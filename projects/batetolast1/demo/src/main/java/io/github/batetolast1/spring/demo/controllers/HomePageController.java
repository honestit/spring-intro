package io.github.batetolast1.spring.demo.controllers;

import io.github.batetolast1.spring.demo.dto.CategoryDTO;
import io.github.batetolast1.spring.demo.dto.ShowAdvertDTO;
import io.github.batetolast1.spring.demo.service.impl.DefaultAdvertService;
import io.github.batetolast1.spring.demo.service.impl.DefaultCategoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/")
@Log4j2
public class HomePageController {

    private final DefaultAdvertService defaultAdvertService;
    private final DefaultCategoryService defaultCategoryService;

    @Autowired
    public HomePageController(DefaultAdvertService defaultAdvertService, DefaultCategoryService defaultCategoryService) {
        this.defaultAdvertService = defaultAdvertService;
        this.defaultCategoryService = defaultCategoryService;
    }

    @GetMapping
    public String prepareHomePage(Model model) {
        List<ShowAdvertDTO> advertDTOs = defaultAdvertService.getAdverts();
        model.addAttribute("advertDTOs", advertDTOs);
        return "home-page";
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        List<CategoryDTO> categoryDTOs = defaultCategoryService.getAllCategories();
        model.addAttribute("categoryDTOs", categoryDTOs);
    }
}
