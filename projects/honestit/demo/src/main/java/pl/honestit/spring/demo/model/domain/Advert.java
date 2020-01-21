package pl.honestit.spring.demo.model.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "adverts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "owner") @EqualsAndHashCode(of = "id")
public class Advert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;
    private LocalDateTime posted;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    @Column(name = "owner_id", insertable = false, updatable = false)
    private Long ownerId;
}
