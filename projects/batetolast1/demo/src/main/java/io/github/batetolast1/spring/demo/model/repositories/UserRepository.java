package io.github.batetolast1.spring.demo.model.repositories;

import io.github.batetolast1.spring.demo.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);

    User findByUsername(String username);

    List<User> findAllByUsername(String username);
}

// interfejs JpaRepository automatycznie dostarcza implementację DAO w standardzie JPA dla encji User, tworząc repozytorium na podstawie modułu Spring Data JPA
//
// Metody dostępne poprzez taką implementację:
// List<T> findAll();
// List<T> findAll(Sort var1);
// List<T> findAllById(Iterable<ID> var1);
// <S extends T> List<S> saveAll(Iterable<S> var1);
// void flush();
// <S extends T> S saveAndFlush(S var1);
// void deleteInBatch(Iterable<T> var1);
// void deleteAllInBatch();
// T getOne(ID var1);
// <S extends T> List<S> findAll(Example<S> var1);
// <S extends T> List<S> findAll(Example<S> var1, Sort var2);
