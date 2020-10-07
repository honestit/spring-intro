package io.github.batetolast1.spring.demo.service.impl;

import io.github.batetolast1.spring.demo.dto.DeleteAdvertDTO;
import io.github.batetolast1.spring.demo.dto.EditAdvertDTO;
import io.github.batetolast1.spring.demo.dto.ObserveAdvertDTO;
import io.github.batetolast1.spring.demo.dto.UnobserveAdvertDTO;
import io.github.batetolast1.spring.demo.model.domain.Advert;
import io.github.batetolast1.spring.demo.model.domain.User;
import io.github.batetolast1.spring.demo.model.repositories.AdvertRepository;
import io.github.batetolast1.spring.demo.model.repositories.CategoryRepository;
import io.github.batetolast1.spring.demo.model.repositories.UserRepository;
import io.github.batetolast1.spring.demo.security.AuthenticationFacade;
import io.github.batetolast1.spring.demo.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final AdvertRepository advertRepository;
    private final AuthenticationFacade authenticationFacade;

    @Autowired
    public DefaultUserService(UserRepository userRepository, AdvertRepository advertRepository, AuthenticationFacade authenticationFacade) {
        this.userRepository = userRepository;
        this.advertRepository = advertRepository;
        this.authenticationFacade = authenticationFacade;
    }

    @Override
    public void deleteAdvert(DeleteAdvertDTO deleteAdvertDTO) {
        User loggedUser = getUser(currentUsername());
        log.info("Logged user={}", loggedUser);

        Optional<Advert> optionalAdvert = advertRepository.findById(deleteAdvertDTO.getAdvertId());

        if (optionalAdvert.isPresent()) {
            Advert advert = optionalAdvert.get();
            log.info("Advert to delete={}", advert);

            if (loggedUser == advert.getOwner()) {
                advertRepository.delete(advert);
                log.info("Advert deleted!");
            } else {
                log.info("Advert wasn't created by logged user, deleting failed!");
            }
        } else {
            log.info("Advert not found, id={}", deleteAdvertDTO.getAdvertId());
        }
    }

    @Override
    public void editAdvert(EditAdvertDTO editAdvertDTO) {
        User loggedUser = getUser(currentUsername());
        log.info("Logged user={}", loggedUser);

        Optional<Advert> optionalAdvert = advertRepository.findById(editAdvertDTO.getAdvertId());

        if (optionalAdvert.isPresent()) {
            Advert advert = optionalAdvert.get();
            log.info("Advert to edit={}", advert);

            if (loggedUser == advert.getOwner()) {
                advert.setTitle(editAdvertDTO.getTitle());
                advert.setDescription(editAdvertDTO.getDescription());
                log.info("Advert to update={}", advert);

                advertRepository.save(advert);
                log.info("Updated advert={}", advert);
            } else {
                log.info("Advert wasn't created by logged user, can't edit!");
            }
        }

        log.info("Advert with id={} doesn't exist!", editAdvertDTO.getAdvertId());
    }

    @Override
    public void observeAdvert(ObserveAdvertDTO observeAdvertDTO) {
        User loggedUser = getUser(currentUsername());
        log.info("Logged user={}", loggedUser);

        Optional<Advert> optionalAdvert = advertRepository.findById(observeAdvertDTO.getAdvertId());

        if (optionalAdvert.isPresent()) {
            Advert advert = optionalAdvert.get();
            log.info("Advert to observe={}", advert);

            if (advert.getOwner() != loggedUser) {
                loggedUser.getObservedAdverts().add(advert);
                userRepository.save(loggedUser);
                log.info("Advert observed!");
            } else {
                log.info("Advert was created by logged user, can't be observed");
            }
        } else {
            log.info("Advert with id={} doesn't exist!", observeAdvertDTO.getAdvertId());
        }
    }

    @Override
    public void unobserveAdvert(UnobserveAdvertDTO unobserveAdvertDTO) {
        User loggedUser = getUser(currentUsername());
        log.info("Logged user={}", loggedUser);

        Optional<Advert> optionalAdvert = advertRepository.findById(unobserveAdvertDTO.getAdvertId());

        if (optionalAdvert.isPresent()) {
            Advert advert = optionalAdvert.get();
            log.info("Advert to unobserve={}", advert);

            if (loggedUser.getObservedAdverts().remove(advert)) {
                userRepository.save(loggedUser);
                log.info("Advert unobserved!");
            } else {
                log.info("Advert was not observed, thus can't be unobserved");
            }
        } else {
            log.info("Advert with id={} doesn't exist!", unobserveAdvertDTO.getAdvertId());
        }
    }

    private User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    private String currentUsername() {
        Authentication authentication = authenticationFacade.getAuthentication();
        return authentication.getName();
    }
}
