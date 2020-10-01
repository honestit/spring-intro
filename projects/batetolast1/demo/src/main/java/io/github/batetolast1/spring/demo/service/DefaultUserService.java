package io.github.batetolast1.spring.demo.service;

import io.github.batetolast1.spring.demo.model.domain.User;
import io.github.batetolast1.spring.demo.model.repositories.AdvertRepository;
import io.github.batetolast1.spring.demo.model.repositories.CategoryRepository;
import io.github.batetolast1.spring.demo.model.repositories.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final AdvertRepository advertRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public DefaultUserService(UserRepository userRepository, AdvertRepository advertRepository, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.advertRepository = advertRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
