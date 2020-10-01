package io.github.batetolast1.spring.demo.service;

import io.github.batetolast1.spring.demo.dto.CreateAdvertDTO;
import io.github.batetolast1.spring.demo.dto.ShowAdvertDTO;
import io.github.batetolast1.spring.demo.model.domain.Advert;
import io.github.batetolast1.spring.demo.model.domain.Category;
import io.github.batetolast1.spring.demo.model.domain.User;
import io.github.batetolast1.spring.demo.model.repositories.AdvertRepository;
import io.github.batetolast1.spring.demo.model.repositories.CategoryRepository;
import io.github.batetolast1.spring.demo.model.repositories.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class DefaultAdvertService implements AdvertService {

    private final UserRepository userRepository;
    private final AdvertRepository advertRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public DefaultAdvertService(UserRepository userRepository, AdvertRepository advertRepository, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.advertRepository = advertRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void addAdvert(CreateAdvertDTO createAdvertDTO, String username) {
        User loggedUser = getUser(username);
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
    public List<ShowAdvertDTO> getAdverts(Principal principal) {
        return principal == null ? getFirst10Adverts() : getAllAdverts(principal.getName());
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

    private List<ShowAdvertDTO> getAllAdverts(String username) {
        User loggedUser = getUser(username);
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

    private User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    private Category getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(IllegalArgumentException::new);
    }
}
