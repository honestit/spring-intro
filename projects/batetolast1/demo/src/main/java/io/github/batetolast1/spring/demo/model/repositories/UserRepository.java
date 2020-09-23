package io.github.batetolast1.spring.demo.model.repositories;

import io.github.batetolast1.spring.demo.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
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
