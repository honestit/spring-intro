package pl.sda.projects.adverts.model.domain;

import lombok.*;

import javax.persistence.*;

@Entity
// Spring Boot konfiguruje własny mechanizm nazewniczy
// który domyślnie nazwy tabel tworzy przez stworzenie
// liczby mnogiej z nazwy klasy i wszystko małymi
// literami
@Table(name = "users")
@Getter @Setter @ToString(exclude = "password") @EqualsAndHashCode(of = "id")
@AllArgsConstructor @NoArgsConstructor @Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    // Spring Boot konfiguruje własny mechanizm nazewniczy
    // który domyślnie zamienia camel case na uderscore
    // (_) cale i wszystko z małych liter
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private Boolean active;
}
