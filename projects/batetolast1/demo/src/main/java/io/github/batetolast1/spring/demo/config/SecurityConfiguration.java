package io.github.batetolast1.spring.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

// dodajemy własną konfigurację bezpieczeństwa

@Configuration // klasa konfiguracji musi posiadać adnotację @Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter { // dodatkowo musi rozszerzać klasę WebSecurityConfigurerAdapter

    // wstrzykujemy obiekt javax.sql.DataSource skonfigurowany w pliku application.properties
    private final DataSource dataSource;

    // wstrzyknięcie przez konstruktor
    @Autowired
    public SecurityConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // umożliwienie wejścia na stronę rejestracji bez logowania
    @Override
    protected void configure(HttpSecurity security) throws Exception { // nadpisując metodę configure() podajemy konfigurację bezpieczeństwa
        security.authorizeRequests()
                .antMatchers("/").permitAll() // podajemy ścieżkę do strony głównej, żeby każdy mógł na nią wejść
                .antMatchers("/register").anonymous() // pozwalamy wejść na /register wszystkim niezalogowanym użytkownikom
                .antMatchers("/login").anonymous()
                .anyRequest().authenticated()  // zabezpieczenie innych ścieżek tylko dla zalogowanych użytkowników - każde inne żądanie musi być poprzedzone logowaniem
                // dzięki temu zawsze uzyskamy nie-nullowy obiekt klasy Principal
                .and()
                // aktywowanie logowania przez automatyczny formularz logowania Spring Security
                .formLogin()
                // wskazanie ścieżki logowania obsługiwanej przez kontroler
                .loginPage("/login")
                // podajemy domyślną ścieżkę do kontrolera po udanym logowaniu
                .defaultSuccessUrl("/")
                .and()
                // aktywujemy wylogowywanie automatycznie przeprowadzane przez Spring Security
                .logout()
                // podajemy domyślną ścieżkę do kontrolera po udanym wylogowaniu
                .logoutSuccessUrl("/");

        // w chainie przekazujemy obiekt klasy HttpSecurity
    }

    // dodanie szyfrowania hasła

    // adnotacja @Bean tworzy obiekt bean zarządzany przez Springa, dzięki czemu Spring umożliwia później wstrzykiwanie takiego obiektu
    @Bean
    public PasswordEncoder passwordEncoder() {
        // zwracamy obiekt klasy DelegatingPasswordEncoder, który automatycznie dostosowuje odpowiednią implementację algorytmu do zapisanego hasła
        // domyślnie Spring korzysta z algorytmu bcrypt dostarczanego przez klasę BCryptPasswordEncoder
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // dodanie własnej implementacji zasad autentykacji użytkownika - zapytania SQL i technologia JDBC
    @Override
    protected void configure(AuthenticationManagerBuilder authBuilder) throws Exception {
        authBuilder.jdbcAuthentication()
                .dataSource(dataSource) // określenie źródła danych
                .passwordEncoder(passwordEncoder()) // określenie mechanizmu szyfrowania hasła
                .usersByUsernameQuery("SELECT username, password, active FROM users WHERE username = ?") // zapytanie SQL zwracające nazwę użytkownika, hasło użytkownika i flagę aktywności na podstawie nazwy użytkownika
                .authoritiesByUsernameQuery("SELECT username, 'ROLE_USER' FROM users WHERE username = ?"); // zapytanie SQL zwracające zestaw par nazwa użytkownika-rola użytkownika na podstawie nazwy użytkownika

        // ostatnie zapytanie domyślnie przypisuje rolę użytkownikowi - nie ma takiego pola w bazie danych
        // TODO add user roles support: https://www.baeldung.com/role-and-privilege-for-spring-security-registration
    }
}
