package io.github.batetolast1.spring.demo.controllers;

import io.github.batetolast1.spring.demo.dto.CategoryDTO;
import io.github.batetolast1.spring.demo.dto.ShowAdvertDTO;
import io.github.batetolast1.spring.demo.model.domain.Advert;
import io.github.batetolast1.spring.demo.model.domain.Category;
import io.github.batetolast1.spring.demo.model.domain.User;
import io.github.batetolast1.spring.demo.model.repositories.AdvertRepository;
import io.github.batetolast1.spring.demo.model.repositories.CategoryRepository;
import io.github.batetolast1.spring.demo.model.repositories.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
@Log4j2
public class HomePageController {

    private final UserRepository userRepository;
    private final AdvertRepository advertRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public HomePageController(UserRepository userRepository, AdvertRepository advertRepository, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.advertRepository = advertRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public String prepareHomePage(Principal principal, Model model) {
        User loggedUser = (principal != null) ? userRepository.findByUsername(principal.getName()) : null;
        log.info("Logged user={}", loggedUser);

        List<Advert> adverts = (principal != null) ? advertRepository.findAllByOrderByPostedDesc() : advertRepository.findFirst10ByOrderByPostedDesc();
        log.info("All adverts={}", adverts);

        List<ShowAdvertDTO> advertDTOs = adverts.stream().map(advert -> {
            ShowAdvertDTO advertDTO = new ShowAdvertDTO();
            advertDTO.setId(advert.getId());
            advertDTO.setTitle(advert.getTitle());
            advertDTO.setDescription(advert.getDescription());
            advertDTO.setOwnerId(advert.getOwner().getId());
            advertDTO.setOwnerUsername(advert.getOwner().getUsername());
            advertDTO.setCategoryName(advert.getCategory().getName());
            advertDTO.setPosted(advert.getPosted().format(DateTimeFormatter.ofPattern("HH:mm, dd.MM.yyyy")));
            advertDTO.setCreatedByLoggedUser(loggedUser != null && loggedUser == advert.getOwner());
            advertDTO.setObserved(loggedUser != null && loggedUser.getObservedAdverts().contains(advert));
            return advertDTO;
        }).collect(Collectors.toList());
        model.addAttribute("advertDTOs", advertDTOs);

        return "home-page";
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        List<Category> categories = categoryRepository.findAll();
        log.info("Loaded categories={}", categories);

        List<CategoryDTO> categoryDTOs = categories.stream().map(category -> {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(category.getId());
            categoryDTO.setName(category.getName());
            return categoryDTO;
        }).collect(Collectors.toList());
        model.addAttribute("categoryDTOs", categoryDTOs);
    }
}
