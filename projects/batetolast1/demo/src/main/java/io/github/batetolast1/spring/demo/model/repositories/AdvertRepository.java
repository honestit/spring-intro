package io.github.batetolast1.spring.demo.model.repositories;

import io.github.batetolast1.spring.demo.model.domain.Advert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdvertRepository extends JpaRepository<Advert, Long> {
    List<Advert> findAllByOrderByPostedDesc();
}
