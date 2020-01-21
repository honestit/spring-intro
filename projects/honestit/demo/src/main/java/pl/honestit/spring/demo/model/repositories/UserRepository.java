package pl.honestit.spring.demo.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.honestit.spring.demo.model.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User getByUsername(String username);
}
