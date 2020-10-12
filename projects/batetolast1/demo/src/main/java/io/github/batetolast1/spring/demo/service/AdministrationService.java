package io.github.batetolast1.spring.demo.service;

import io.github.batetolast1.spring.demo.dto.DeleteCategoryDTO;
import io.github.batetolast1.spring.demo.dto.EditCategoryDTO;

public interface AdministrationService {

    void deleteCategory(DeleteCategoryDTO deleteCategoryDTO);

    void editCategory(EditCategoryDTO editCategoryDTO);
}
