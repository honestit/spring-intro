package pl.sda.projects.adverts.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sda.projects.adverts.model.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
