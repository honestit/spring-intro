package io.github.batetolast1.spring.demo.service;

import io.github.batetolast1.spring.demo.dto.*;

import java.util.List;

public interface CategoryService {

    List<ShowCategoryDTO> getAllCategories();

    void addCategory(CreateCategoryDTO createCategoryDTO);

    EditCategoryDTO getEditedCategory(Long categoryId);
}
