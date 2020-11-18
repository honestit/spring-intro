package io.github.batetolast1.spring.demo.model.repositories;

import io.github.batetolast1.spring.demo.model.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role getByName(String name);
}
