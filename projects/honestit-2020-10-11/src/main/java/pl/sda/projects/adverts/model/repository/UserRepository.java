package pl.sda.projects.adverts.model.repository;

import jdk.javadoc.doclet.Reporter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import pl.sda.projects.adverts.model.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    User getByUsername(String username);
}
