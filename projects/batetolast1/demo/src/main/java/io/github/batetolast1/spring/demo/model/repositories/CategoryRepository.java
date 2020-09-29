package io.github.batetolast1.spring.demo.model.repositories;

import io.github.batetolast1.spring.demo.model.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findById(Long id);
}
