package io.github.batetolast1.spring.demo.model.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "roles")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Role extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private String name;
}
