package io.github.batetolast1.spring.demo.model.domain;

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
@ToString(exclude = "password")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    // ID
    @Id // pole klucza głównego
    @GeneratedValue(strategy = GenerationType.IDENTITY) // pozwala na automatyczne generowanie wartości klucza głównego
    @EqualsAndHashCode.Include
    private Long id;

    // nazwa użytkownika
    @Column(unique = true, nullable = false) // nazwa użytkownika musi być unikalna i nie może być pusta
    @EqualsAndHashCode.Include
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
}
