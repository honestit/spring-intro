package io.github.batetolast1.spring.demo.service.impl;

import io.github.batetolast1.spring.demo.dto.CreateAdvertDTO;
import io.github.batetolast1.spring.demo.dto.EditAdvertDTO;
import io.github.batetolast1.spring.demo.dto.ShowAdvertDTO;
import io.github.batetolast1.spring.demo.dto.ShowUserDTO;
import io.github.batetolast1.spring.demo.model.domain.Advert;
import io.github.batetolast1.spring.demo.model.domain.Category;
import io.github.batetolast1.spring.demo.model.domain.User;
import io.github.batetolast1.spring.demo.model.repositories.AdvertRepository;
import io.github.batetolast1.spring.demo.model.repositories.CategoryRepository;
import io.github.batetolast1.spring.demo.model.repositories.UserRepository;
import io.github.batetolast1.spring.demo.security.AuthenticationFacade;
import io.github.batetolast1.spring.demo.service.AdvertService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Log4j2
public class DefaultAdvertService implements AdvertService {

    private final UserRepository userRepository;
    private final AdvertRepository advertRepository;
    private final CategoryRepository categoryRepository;
    private final AuthenticationFacade authenticationFacade;

    @Autowired
    public DefaultAdvertService(UserRepository userRepository, AdvertRepository advertRepository, CategoryRepository categoryRepository, AuthenticationFacade authenticationFacade) {
        this.userRepository = userRepository;
        this.advertRepository = advertRepository;
        this.categoryRepository = categoryRepository;
        this.authenticationFacade = authenticationFacade;
    }

    @Override
    public void addAdvert(CreateAdvertDTO createAdvertDTO) {
        User loggedUser = getUser(currentUsername());
        Category category = getCategory(createAdvertDTO.getCategoryId());

        Advert advert = Advert.builder()
                .title(createAdvertDTO.getTitle())
                .description(createAdvertDTO.getDescription())
                .owner(loggedUser)
                .category(category)
                .build();
        log.info("Advert to add to DB: {}", advert);

        advertRepository.save(advert);
        log.info("Advert added to DB: {}", advert);
    }

    @Override
    public List<ShowAdvertDTO> getHomePageAdverts() {
        User loggedUser = getUser(currentUsername());
        return loggedUser == null ? getFirst10Adverts() : getAllAdverts();
    }

    private List<ShowAdvertDTO> getFirst10Adverts() {
        List<Advert> adverts = advertRepository.findFirst10ByOrderByCreatedOnDesc();
        log.info("First 10 adverts={}", adverts);

        return adverts
                .stream()
                .map(advert -> {
                    ShowAdvertDTO advertDTO = new ShowAdvertDTO();
                    advertDTO.setId(advert.getId());
                    advertDTO.setTitle(advert.getTitle());
                    advertDTO.setDescription(advert.getDescription());
                    advertDTO.setOwnerId(advert.getOwner().getId());
                    advertDTO.setOwnerUsername(advert.getOwner().getUsername());
                    advertDTO.setCategoryName(advert.getCategory().getName());
                    advertDTO.setPosted(advert.getCreatedOn().format(DateTimeFormatter.ofPattern("HH:mm, dd.MM.yyyy")));
                    advertDTO.setCreatedByLoggedUser(false);
                    advertDTO.setObserved(false);
                    return advertDTO;
                })
                .collect(Collectors.toList());
    }

    private List<ShowAdvertDTO> getAllAdverts() {
        User loggedUser = getUser(currentUsername());
        log.info("Logged user={}", loggedUser);

        List<Advert> adverts = advertRepository.findAllByOrderByCreatedOnDesc();
        log.info("All adverts={}", adverts);

        return adverts
                .stream()
                .map(advert -> {
                    ShowAdvertDTO advertDTO = new ShowAdvertDTO();
                    advertDTO.setId(advert.getId());
                    advertDTO.setTitle(advert.getTitle());
                    advertDTO.setDescription(advert.getDescription());
                    advertDTO.setOwnerId(advert.getOwner().getId());
                    advertDTO.setOwnerUsername(advert.getOwner().getUsername());
                    advertDTO.setCategoryName(advert.getCategory().getName());
                    advertDTO.setPosted(advert.getCreatedOn().format(DateTimeFormatter.ofPattern("HH:mm, dd.MM.yyyy")));
                    advertDTO.setCreatedByLoggedUser(loggedUser != null && loggedUser == advert.getOwner());
                    advertDTO.setObserved(loggedUser != null && loggedUser.getObservedAdverts().contains(advert));
                    return advertDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ShowAdvertDTO> getUserAdverts(Long id) {
        User loggedUser = getUser(currentUsername());
        log.info("Logged user={}", loggedUser);

        User advertsOwner = getAdvertsOwner(id);

        if (advertsOwner == null) {
            return null;
        }

        List<Advert> ownersAdverts = advertRepository.findAllByOwnerOrderByCreatedOnDesc(advertsOwner);
        log.info("Owner's adverts={}", ownersAdverts);

        List<ShowAdvertDTO> ownerAdvertDTOs = ownersAdverts.stream().map(advert -> {
            ShowAdvertDTO advertDTO = new ShowAdvertDTO();
            advertDTO.setId(advert.getId());
            advertDTO.setTitle(advert.getTitle());
            advertDTO.setDescription(advert.getDescription());
            advertDTO.setOwnerId(advert.getOwner().getId());
            advertDTO.setOwnerUsername(advert.getOwner().getUsername());
            advertDTO.setCategoryName(advert.getCategory().getName());
            advertDTO.setPosted(advert.getCreatedOn().format(DateTimeFormatter.ofPattern("HH:mm, dd.MM.yyyy")));
            advertDTO.setCreatedByLoggedUser(loggedUser != null && loggedUser == advert.getOwner());
            advertDTO.setObserved(loggedUser != null && loggedUser.getObservedAdverts().contains(advert));
            return advertDTO;
        }).collect(Collectors.toList());

        return ownerAdvertDTOs;
    }


    private User getAdvertsOwner(Long id) {
        User loggedUser = getUser(currentUsername());
        User advertsOwner;
        if (id == null) {
            advertsOwner = loggedUser;
        } else {
            Optional<User> optionalUser = userRepository.findById(id);
            if (optionalUser.isEmpty()) {
                log.info("Adverts' owner not found, id={}", id);
                return null;
            }
            advertsOwner = optionalUser.get();
        }
        return advertsOwner;
    }

    @Override
    public ShowUserDTO getAdvertsOwnerUsername(Long id) {
        User advertsOwner = getAdvertsOwner(id);
        if (advertsOwner == null) {
            return null;
        } else {
            ShowUserDTO userDTO = new ShowUserDTO();
            userDTO.setUsername(advertsOwner.getUsername());
            return userDTO;
        }
    }

    @Override
    public List<ShowAdvertDTO> getObservedAdverts() {
        User loggedUser = getUser(currentUsername());
        log.info("Logged user={}", loggedUser);

        Set<Advert> observedAdverts = loggedUser.getObservedAdverts();
        log.info("Observed adverts={}", observedAdverts);

        List<ShowAdvertDTO> observedAdvertDTOs = observedAdverts.stream().map(advert -> {
            ShowAdvertDTO advertDTO = new ShowAdvertDTO();
            advertDTO.setId(advert.getId());
            advertDTO.setTitle(advert.getTitle());
            advertDTO.setDescription(advert.getDescription());
            advertDTO.setOwnerId(advert.getOwner().getId());
            advertDTO.setOwnerUsername(advert.getOwner().getUsername());
            advertDTO.setCategoryName(advert.getCategory().getName());
            advertDTO.setPosted(advert.getCreatedOn().format(DateTimeFormatter.ofPattern("HH:mm, dd.MM.yyyy")));
            advertDTO.setCreatedByLoggedUser(loggedUser == advert.getOwner());
            advertDTO.setObserved(loggedUser.getObservedAdverts().contains(advert));
            return advertDTO;
        }).collect(Collectors.toList());
        return observedAdvertDTOs;
    }

    @Override
    public EditAdvertDTO getEditedAdvert(Long id) {
        User loggedUser = getUser(currentUsername());
        log.info("Logged user={}", loggedUser);

        Optional<Advert> optionalAdvert = advertRepository.findById(id);

        if (optionalAdvert.isPresent()) {
            Advert advert = optionalAdvert.get();
            log.info("Advert to edit={}", advert);

            if (loggedUser != advert.getOwner()) {
                log.info("Advert wasn't created by logged user, can't edit!");
                return null;
            }

            EditAdvertDTO editAdvertDTO = new EditAdvertDTO();
            editAdvertDTO.setAdvertId(advert.getId());
            editAdvertDTO.setTitle(advert.getTitle());
            editAdvertDTO.setDescription(advert.getDescription());

            return editAdvertDTO;
        }

        log.info("Advert with id={} doesn't exist!", id);
        return null;
    }


    private User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    private String currentUsername() {
        Authentication authentication = authenticationFacade.getAuthentication();
        return authentication.getName();
    }

    private Category getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(IllegalArgumentException::new);
    }
}
