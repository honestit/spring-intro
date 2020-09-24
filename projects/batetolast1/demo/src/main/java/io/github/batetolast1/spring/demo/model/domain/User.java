package io.github.batetolast1.spring.demo.model.domain;

import com.sun.xml.bind.v2.TODO;
import lombok.*;

import javax.persistence.*;

// Spring
@Entity // tworzy z klasy tzw. encję
@Table(name = "users") // mapuje klasę do tabeli "users" w bazie danych

// Lombok
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "password")
@EqualsAndHashCode(of = "id")
public class User {
    // ID
    @Id // pole klucza głównego
    @GeneratedValue(strategy = GenerationType.IDENTITY) // pozwala na automatyczne generowanie wartości klucza głównego
    private Long id;

    // nazwa użytkownika
    @Column(unique = true, nullable = false) // nazwa użytkownika musi być unikalna i nie może być pusta
    private String username;

    // hasło
    @Column(nullable = false) // hasło nie może być puste
    private String password;

    // imię i nazwisko użytkownika
    @Column(name = "first_name", nullable = false) // podajemy nazwę kolumny zgodnie z konwencją SQL, pole nie może być puste
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;

    // czy użytkownik jest aktywny
    @Column(nullable = false) // pole nie może być puste
    private boolean active;

    //TODO add @OneToMany with mappedBy to future List<Advert>, add (fetch = FetchType.LAZY) (https://www.baeldung.com/jpa-join-column)?
}
