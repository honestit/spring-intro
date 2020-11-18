package io.github.batetolast1.spring.demo.model.repositories;

import io.github.batetolast1.spring.demo.model.domain.Category;
import io.github.batetolast1.spring.demo.model.domain.enums.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByCategoryType(CategoryType categoryType);

    Optional<Category> findByIdAndCategoryType(Long id, CategoryType categoryType);
}
