package pl.sda.projects.adverts.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.sda.projects.adverts.model.domain.Advert;
import pl.sda.projects.adverts.model.domain.User;
import pl.sda.projects.adverts.model.repository.AdvertRepository;
import pl.sda.projects.adverts.model.repository.UserRepository;

import java.time.LocalDateTime;

@Component @Slf4j @RequiredArgsConstructor
public class StartupDataLoader {

    // Mamy wygenerowany konstruktor dla wszystkich
    // pól typu final przez lomboka (@RequiredArgsConstructor)
    // więc nie tworzymy własnego, a jak mamy
    // tylko jeden, to Spring automatycznie (magicznie)
    // używa go do wstrzykiwania zależności
    private final UserRepository userRepository;
    private final AdvertRepository advertRepository;
    private final PasswordEncoder passwordEncoder;

    @EventListener
    public void onStartupPrepareData(ContextRefreshedEvent event) {
        log.info("Loading startup data...");
        userRepository.save(User.builder()
                .firstName("Dupa")
                .lastName("Dupa")
                .username("andrzej")
                .password(passwordEncoder.encode("andrzej"))
                .active(true)
                .build());

        User andrzej = userRepository.getByUsername("andrzej");
        advertRepository.save(Advert.builder()
                .title("Kupię psa")
                .description("Kupię ładnego, spokojnego psa")
                .user(andrzej)
                .posted(LocalDateTime.now())
                .build());
        advertRepository.save(Advert.builder()
                .title("Oddam mieszkanie")
                .description("Oddam mieszkanie w centrum")
                .user(andrzej)
                .posted(LocalDateTime.now().minusDays(1))
                .build());
        advertRepository.save(Advert.builder()
                .title("Zjem drzewo")
                .description("Zjem dowolne drzewo do 2m średnicy!")
                .user(andrzej)
                .posted(LocalDateTime.now().minusDays(1).minusHours(4))
                .build());
        log.info("Startup data loaded");
    }



}
