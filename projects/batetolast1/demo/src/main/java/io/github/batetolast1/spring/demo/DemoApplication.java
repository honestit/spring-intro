package io.github.batetolast1.spring.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // adnotacja, która pozwala na domyślną konfigurację aplikacji
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args); // statyczna metoda odpowiedzialna za uruchomienie aplikacji
	}

	// nowe klasy muszą być tworzone w podpakietach pakietu demo (poniżej tej klasy)
}
