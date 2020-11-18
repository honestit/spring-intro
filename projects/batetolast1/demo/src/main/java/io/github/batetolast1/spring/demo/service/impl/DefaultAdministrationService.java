package io.github.batetolast1.spring.demo.service.impl;

import io.github.batetolast1.spring.demo.dto.DeleteCategoryDTO;
import io.github.batetolast1.spring.demo.dto.EditCategoryDTO;
import io.github.batetolast1.spring.demo.model.domain.Category;
import io.github.batetolast1.spring.demo.model.domain.User;
import io.github.batetolast1.spring.demo.model.domain.enums.CategoryType;
import io.github.batetolast1.spring.demo.model.repositories.AdvertRepository;
import io.github.batetolast1.spring.demo.model.repositories.CategoryRepository;
import io.github.batetolast1.spring.demo.model.repositories.RoleRepository;
import io.github.batetolast1.spring.demo.model.repositories.UserRepository;
import io.github.batetolast1.spring.demo.security.AuthenticationFacade;
import io.github.batetolast1.spring.demo.service.AdministrationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class DefaultAdministrationService implements AdministrationService {

    private final CategoryRepository categoryRepository;
    private final AdvertRepository advertRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationFacade authenticationFacade;

    @Autowired
    public DefaultAdministrationService(CategoryRepository categoryRepository,
                                        AdvertRepository advertRepository,
                                        UserRepository userRepository,
                                        RoleRepository roleRepository,
                                        AuthenticationFacade authenticationFacade) {
        this.categoryRepository = categoryRepository;
        this.advertRepository = advertRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationFacade = authenticationFacade;
    }

    @Override
    public void deleteCategory(DeleteCategoryDTO deleteCategoryDTO) {
        User loggedUser = getUser(currentUsername());
        if (!loggedUser.getRoles().contains(roleRepository.getByName("ROLE_ADMIN"))) {
            log.info("Current user: {} is not allowed to delete category", loggedUser);
            return;
        }

        Optional<Category> optionalCategory = categoryRepository.findByIdAndCategoryType(deleteCategoryDTO.getId(), CategoryType.ACTIVE);
        if (optionalCategory.isPresent()) {
            Category categoryToDelete = optionalCategory.get();
            log.info("Category to delete: {}", categoryToDelete);
            if (advertRepository.countByCategory(categoryToDelete) == 0) {
                categoryToDelete.setName("");
                categoryToDelete.setCategoryType(CategoryType.DELETED);
                categoryRepository.save(categoryToDelete);
                log.info("Category deleted");
            } else {
                log.info("Category is used in adverts, can't be deleted!");
            }
        } else {
            log.info("Category with id: {} not found", deleteCategoryDTO.getId());
        }
    }

    @Override
    public void editCategory(EditCategoryDTO editCategoryDTO) {
        User loggedUser = getUser(currentUsername());
        if (!loggedUser.getRoles().contains(roleRepository.getByName("ROLE_ADMIN"))) {
            log.info("Current user: {} is not allowed to edit category", loggedUser);
            return;
        }

        Optional<Category> optionalCategory = categoryRepository.findByIdAndCategoryType(editCategoryDTO.getId(), CategoryType.ACTIVE);
        if (optionalCategory.isPresent()) {
            Category categoryToEdit = optionalCategory.get();
            log.info("Category to edit: {}", categoryToEdit);
            categoryToEdit.setName(editCategoryDTO.getName());
            categoryRepository.save(categoryToEdit);
            log.info("Updated category: {}", categoryToEdit);
        }
    }

    private User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    private String currentUsername() {
        Authentication authentication = authenticationFacade.getAuthentication();
        return authentication.getName();
    }
}
