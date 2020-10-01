package io.github.batetolast1.spring.demo.service;

import io.github.batetolast1.spring.demo.model.domain.User;

public interface UserService {
    User findByUsername(String username);
}
