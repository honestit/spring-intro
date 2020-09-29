package io.github.batetolast1.spring.demo.model.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "categories")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")

public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
}
