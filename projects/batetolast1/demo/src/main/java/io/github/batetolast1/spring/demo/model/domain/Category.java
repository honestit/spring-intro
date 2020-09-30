package io.github.batetolast1.spring.demo.model.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "categories")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Category extends AbstractEntity {

    @Column(nullable = false)
    private String name;
}
