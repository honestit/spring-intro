package io.github.batetolast1.spring.demo.controllers;

import io.github.batetolast1.spring.demo.dto.*;
import io.github.batetolast1.spring.demo.service.impl.DefaultAdministrationService;
import io.github.batetolast1.spring.demo.service.impl.DefaultCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CategoryController {

    private final DefaultCategoryService defaultCategoryService;
    private final DefaultAdministrationService defaultAdministrationService;

    @Autowired
    public CategoryController(DefaultCategoryService defaultCategoryService, DefaultAdministrationService defaultAdministrationService) {
        this.defaultCategoryService = defaultCategoryService;
        this.defaultAdministrationService = defaultAdministrationService;
    }

    @GetMapping("/category")
    public String showAllCategories(Model model) {
        List<ShowCategoryDTO> showCategoryDTOs = defaultCategoryService.getAllCategories();
        model.addAttribute("showCategoryDTOs", showCategoryDTOs);
        return "category/all-categories-page";
    }

    @PostMapping("/category/add")
    public String addCategory(CreateCategoryDTO createCategoryDTO) {
        defaultCategoryService.addCategory(createCategoryDTO);
        return "redirect:/category";
    }

    @PostMapping("/category/delete")
    public String deleteCategory(DeleteCategoryDTO deleteCategoryDTO) {
        defaultAdministrationService.deleteCategory(deleteCategoryDTO);
        return "redirect:/category";
    }

    @GetMapping("/category/edit")
    public String editCategory(Long id, Model model) {
        EditCategoryDTO editCategoryDTO = defaultCategoryService.getEditedCategory(id);
        model.addAttribute("editCategoryDTO", editCategoryDTO);
        return editCategoryDTO == null ? "redirect:/category" : "category/edit-category-form";
    }

    @PostMapping("/category/edit")
    public String editCategory(EditCategoryDTO editCategoryDTO) {
        defaultAdministrationService.editCategory(editCategoryDTO);
        return "redirect:/category";
    }
}
