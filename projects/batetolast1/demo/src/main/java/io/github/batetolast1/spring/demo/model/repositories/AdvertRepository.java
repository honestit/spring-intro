package io.github.batetolast1.spring.demo.model.repositories;

import io.github.batetolast1.spring.demo.model.domain.Advert;
import io.github.batetolast1.spring.demo.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdvertRepository extends JpaRepository<Advert, Long> {

    Optional<Advert> findById(Long id);

    List<Advert> findAllByOrderByCreatedOnDesc();

    List<Advert> findFirst10ByOrderByCreatedOnDesc();

    List<Advert> findAllByOwnerOrderByCreatedOnDesc(User user);

    void deleteById(Long id);
}
