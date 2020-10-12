package io.github.batetolast1.spring.demo.model.repositories;

import io.github.batetolast1.spring.demo.model.domain.Advert;
import io.github.batetolast1.spring.demo.model.domain.Category;
import io.github.batetolast1.spring.demo.model.domain.User;
import io.github.batetolast1.spring.demo.model.domain.enums.AdvertType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdvertRepository extends JpaRepository<Advert, Long> {

    List<Advert> findAllByAdvertTypeOrderByCreatedOnDesc(AdvertType advertType);

    List<Advert> findFirst10ByAdvertTypeOrderByCreatedOnDesc(AdvertType advertType);

    Optional<Advert> findByIdAndAdvertType(Long id, AdvertType advertType);

    List<Advert> findAllByOwnerAndAdvertTypeOrderByCreatedOnDesc(User user, AdvertType advertType);

    void deleteById(Long id);

    Long countByCategory(Category category);
}
