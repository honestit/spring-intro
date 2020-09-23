package io.github.batetolast1.spring.demo.model.domain;

import javax.persistence.*;

// Spring
@Entity // tworzy z klasy tzw. encję
@Table(name = "users") // mapuje klasę do tabeli "users" w bazie danych

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

    public User() {
    }

    public User(Long id, String username, String password, String firstName, String lastName, boolean active) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!id.equals(user.id)) return false;
        return username.equals(user.username);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + username.hashCode();
        return result;
    }
}
