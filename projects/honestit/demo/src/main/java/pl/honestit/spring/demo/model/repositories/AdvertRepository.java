package pl.honestit.spring.demo.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.honestit.spring.demo.model.domain.Advert;

import java.util.List;

public interface AdvertRepository extends JpaRepository<Advert, Long> {
    List<Advert> findAllByOrderByPostedDesc();

    List<Advert> findAllByOwnerUsernameOrderByPostedDesc(String username);

    List<Advert> findFirst10ByOrderByPostedDesc();
}
