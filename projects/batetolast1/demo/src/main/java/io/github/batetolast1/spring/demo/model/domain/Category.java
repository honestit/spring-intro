package io.github.batetolast1.spring.demo.model.domain;

import io.github.batetolast1.spring.demo.model.domain.enums.CategoryType;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "categories")

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString

public class Category extends AbstractEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private CategoryType categoryType;
}
