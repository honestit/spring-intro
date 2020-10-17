package pl.sda.projects.adverts.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.sda.projects.adverts.model.domain.User;
import pl.sda.projects.adverts.model.repository.UserRepository;

@Component @Slf4j @RequiredArgsConstructor
public class StartupDataLoader {

    // Mamy wygenerowany konstruktor dla wszystkich
    // pól typu final przez lomboka (@RequiredArgsConstructor)
    // więc nie tworzymy własnego, a jak mamy
    // tylko jeden, to Spring automatycznie (magicznie)
    // używa go do wstrzykiwania zależności
    private final UserRepository userRepository;
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
        log.info("Startup data loaded");
    }



}
