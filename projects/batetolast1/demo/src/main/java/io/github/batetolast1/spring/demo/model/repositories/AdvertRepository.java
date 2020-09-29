package io.github.batetolast1.spring.demo.model.repositories;

import io.github.batetolast1.spring.demo.model.domain.Advert;
import io.github.batetolast1.spring.demo.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdvertRepository extends JpaRepository<Advert, Long> {

    Optional<Advert> findById(Long id);

    List<Advert> findAllByOrderByPostedDesc();

    List<Advert> findFirst10ByOrderByPostedDesc();

    List<Advert> findAllByOwnerOrderByPostedDesc(User user);

    void deleteById(Long id);
}
