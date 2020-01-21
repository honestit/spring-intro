package pl.honestit.spring.demo.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.honestit.spring.demo.model.domain.Advert;

import java.util.List;
import java.util.Optional;

public interface AdvertRepository extends JpaRepository<Advert, Long> {
    List<Advert> findAllByOrderByPostedDesc();

    List<Advert> findAllByOwnerUsernameOrderByPostedDesc(String username);

    List<Advert> findFirst10ByOrderByPostedDesc();

    Optional<Advert> findByIdAndOwnerUsername(Long advertId, String username);
}
