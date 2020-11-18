package io.github.batetolast1.spring.demo.model.domain;

import io.github.batetolast1.spring.demo.model.domain.enums.AdvertType;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "adverts")

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString

public class Advert extends AbstractEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "text")
    private String description;

    @ManyToOne
    private User owner;

    @OneToOne
    private Category category;

    @Enumerated(EnumType.STRING)
    private AdvertType advertType;
}
