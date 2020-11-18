package io.github.batetolast1.spring.demo.service.impl;

import io.github.batetolast1.spring.demo.dto.CreateCategoryDTO;
import io.github.batetolast1.spring.demo.dto.EditCategoryDTO;
import io.github.batetolast1.spring.demo.dto.ShowCategoryDTO;
import io.github.batetolast1.spring.demo.model.domain.Category;
import io.github.batetolast1.spring.demo.model.domain.enums.CategoryType;
import io.github.batetolast1.spring.demo.model.repositories.AdvertRepository;
import io.github.batetolast1.spring.demo.model.repositories.CategoryRepository;
import io.github.batetolast1.spring.demo.service.CategoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class DefaultCategoryService implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final AdvertRepository advertRepository;

    @Autowired
    public DefaultCategoryService(CategoryRepository categoryRepository, AdvertRepository advertRepository) {
        this.categoryRepository = categoryRepository;
        this.advertRepository = advertRepository;
    }

    @Override
    public List<ShowCategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAllByCategoryType(CategoryType.ACTIVE);
        log.info("Loaded categories={}", categories);

        return categories
                .stream()
                .map(category -> {
                    ShowCategoryDTO categoryDTO = new ShowCategoryDTO();
                    Long advertsCount = advertRepository.countByCategory(category);
                    categoryDTO.setId(category.getId());
                    categoryDTO.setName(category.getName());
                    categoryDTO.setAdvertsCount(advertsCount);
                    categoryDTO.setAllowToDelete(advertsCount == 0);
                    return categoryDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void addCategory(CreateCategoryDTO createCategoryDTO) {
        Category category = Category.builder()
                .name(createCategoryDTO.getName())
                .categoryType(CategoryType.ACTIVE)
                .build();
        log.info("Category to add to DB: {}", category);

        categoryRepository.save(category);
        log.info("Category added to DB: {}", category);
    }

    @Override
    public EditCategoryDTO getEditedCategory(Long categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findByIdAndCategoryType(categoryId, CategoryType.ACTIVE);
        if (optionalCategory.isPresent()) {
            Category categoryToEdit = optionalCategory.get();
            log.info("Category to edit: {}", categoryToEdit);

            EditCategoryDTO editCategoryDTO = new EditCategoryDTO();
            editCategoryDTO.setId(categoryToEdit.getId());
            editCategoryDTO.setName(categoryToEdit.getName());

            return editCategoryDTO;

        }
        log.info("Category with id: {} not found", categoryId);
        return null;
    }
}
