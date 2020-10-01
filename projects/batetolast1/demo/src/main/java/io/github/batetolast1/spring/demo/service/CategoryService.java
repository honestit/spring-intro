package io.github.batetolast1.spring.demo.service;

import io.github.batetolast1.spring.demo.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> getAllCategories();
}
