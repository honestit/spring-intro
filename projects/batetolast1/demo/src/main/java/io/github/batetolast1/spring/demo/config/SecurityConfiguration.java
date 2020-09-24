package io.github.batetolast1.spring.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// dodajemy własną konfigurację bezpieczeństwa, która pozwala wejść na stronę rejestracji bez logowania

@Configuration // klasa konfiguracji musi posiadać adnotację @Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter { // dodatkowo musi rozszerzać klasę WebSecurityConfigurerAdapter

    @Override
    protected void configure(HttpSecurity security) throws Exception { // nadpisując metodę configure() podajemy konfigurację bezpieczeństwa
        security.authorizeRequests()
                    // podajemy ścieżkę do wejścia
                    .antMatchers("/register")
                    // pozwalamy na nią wejść wszystkim użytkownikom
                    .permitAll()
                    // każde inne żądanie musi być poprzedzone logowaniem
                    .anyRequest()
                    .authenticated()
                .and()
                    // aktywowanie logowania przez automatyczny formularz logowania Spring Security
                    .formLogin()
                    // podajemy domyślną stronę sukcesu po logowaniu
                    .defaultSuccessUrl("/index.html")
                .and()
                    // aktywujemy wylogowywanie automatycznie przeprowadzane przez Spring Security
                    .logout()
                    // podajemy domyślną stronę sukcesu po wylogowaniu
                    .logoutSuccessUrl("/index.html");

        // w chainie przekazujemy obiekt klasy HttpSecurity
    }
}
