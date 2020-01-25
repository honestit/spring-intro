[< Powrót](../README.md)

[Wstecz](../module_3/README.md) | Dalej

## Moduł 4. Rozszerzenia architektoniczne i technologiczne

W ramach tej dodatkowej sekcji zrealizujemy zestaw rozszerzeń projektu, które w pierwszej kolejności będą poprawiały architekturę naszej aplikacji (rozszerzały ją), a w drugiej wprowadzały dodatkowe zagadnienia techniczne usprawniające i czyniące cały projekt bardziej atrakcyjny _w oczach innego programisty_.

**Zaczynajmy**

---

### Przegląd zadań do realizacji

Poniższą listę zadań staraj się wykonać zgodnie z zaprezentowaną kolejnością ponieważ część późniejszych zadań może zakładać realizację zadań wcześniejszych. Lista zadań do realizacji wygląda następująco:

- konfiguracja widoków z pomocą ViewResolver,
- wykorzystanie wzorca DTO w postaci obiektów typu `xxxDTO` lub `xxxRequest` w warstwie widoku i kontrolera do obsługi formularzy,
- wykorzystanie wzorca DTO w postaci obiektów typu `xxxDTO` lub `xxxView` w warstwie widoku i kontrolera do prezentacji danych,
- wprowadzenia słownika kategorii do obsługi ogłoszeń,
- wdrożenie warstwy servisów w postaci par interfejsu `xxxService` i implementacji `xxxDefaultService` do separacji logiki biznesowej od logiki widoku,
- wykorzystanie klasy encji bazowej `AbstractEntity` lub `BaseEntity`,
- wprowadzenie ról użytkowników i implementacja funkcjonalności zarządzania kategoriami,
- wykorzystanie biblioteki `org.modelmapper` do usprawnienia konwersji między klasami encji i obiektami transferowymi,
- wprowadzenie walidacji danych na obiektach transferowych (`xxxDTO` lub `xxxRequest`) z wykorzystaniem _Bean Validation_ i formularzy springa (_Spring Forms_),

---

### Zadanie 4.1. Konfiguracja widoków z pomocą ViewResolver

Zadanie możesz zrealizować na dwa sposoby:

**Opcja 1:** Wprowadzając klasę konfiguracyjną, która będzie implementowała interfejs `WebMvcConfigurer` i w niej dostarczając metodę typu `@Bean`, zwracającą poprawnie skonfigurowany obiekt `InternalResourceViewResolver`.

> Pamiętaj tylko, że sama metoda musi zwracać typ `ViewResolver` oraz nazywać się `viewResolver`.
  
**Opcja 2:** Korzystając z ustawień konfiguracyjnych w pliku `application.properties`: `spring.mvc.view.prefix` oraz `spring.mvc.view.suffix`.

W obu przypadkach nie zapomnij dostosować używanych już do tej pory identyfikatorów widoków w kontrolerach.

:hamster: _Coś tu na pewno przyniosę!_

---

### Zadanie 4.2. Wykorzystanie wzorca DTO w warstwie widoku i kontrolera do obsługi formularzy

Zadanie zrealizuj w następujących krokach:

1. Zidentyfikuj w dotychczasowym projekcie funkcjonalności, w których wykorzystywane i przetwarzane są formularze (poza logowaniem). 

   > Wybieraj nawet takie funkcjonalności, gdzie przesyłany jest jeden parametr z formularza,
   
1. Dla każdej zidentyfikowanej funkcjonalności określ, jakie parametry są przesyłane z formularza do kontrolera.
1. Dla każdej zidentyfikowanej funkcjonalności stwórz osobną klasę typu `DataTransferObject`, która będzie się składać z pól odpowiadającym parametrom wskazanym w poprzednim punkcie.

   > Utwórz osobną klasę nawet wtedy, gdy zestawy pól są identyczne albo bardzo podobne: np. przy formularzu tworzenia czy formularzu edytowania powinieneś/powinnaś uzyskać dwie różne klasy.
   
   > Pamiętaj tylko, że w klasach typu DTO nie powinno być pól typu Encji. Często pole, które jest encją w obiekcie DTO możesz traktować jako pole typu `Long` reprezentujące `id` tej encji.

1. W kontrolerach, na obsłudze formularzy (metody `POST`) zamień użyte parametry żądania na wprowadzone obiekty transferowe.

   > Spring sam poradzi sobie z utworzeniem twoich obiektów oraz wypełnieniem ich danymi tak długo, jak będzie w nich dostępny konstruktor bezargumentowy, a pola będą nazywały się tak jak parametry i będą posiadały _gettery_ i _settery_.
   
   > Będziesz też musiał/a poprawić kod tworzący encję, aby powstawały one w oparciu o obiekty transferowe, a nie parametry żądania.
   
Po zrealizowaniu zadania przetestuj działanie formularzy, aby mieć pewność, że nic się po drodze nie popsuło.

:hamster: _Coś tu na pewno przyniosę!_

---

### Zadanie 4.3. Wykorzystanie wzorca DTO w warstwie widoku i kontrolera do prezentacji danych

Zadanie zrealizuj w następujących krokach:

1. Zidentyfikuj w dotychczasowym projekcie funkcjonalności, w których w modelu (obiekcie klasy `Model`) umieszczamy encje, aby wykorzystać je do generowania widoku (na stronach `jsp`).
1. Dla każdej zidentyfikowanej funkcjonalności określ,, jakie pola i w jakim formacie są potrzebne do prezentacji danych w widoku.
1. Dla każdej zidentyfikowanej funkcjonalności stwórz osobną klasę typu `DataTransferObject`, która będzie składać się z pól wskazanym w poprzednim punkcie.

   > W tym wypadku, jeżeli dane na różnych widokach się powtarzają, to nie twórz osobnych klas. Przykładowo: listę ogłoszeń wyświetlamy zarówno na stronie głównej jak i na stronie zalogowanego użytkownika.
   
   > Pamiętaj tylko, że w klasach typu DTO nie powinno być pól typu Encji. Często do prezentacji danych potrzebujesz konkretnych pól z encji powiązanych. Możesz je zatem stworzyć od razu w obiekcie DTO. Przykładowo do wyświetlenia ogłoszenia potrzebujesz pola `username` z encji `User` - możesz więc pole `username` stworzyć od razu w obiekcie transferowym.
   
1. W kontrolerach, na obsłudze wstawiania danych do modelu, zadbaj o konwersję wstawianych do tej pory encji do obiektów typu DTO. Ostatecznie to obiekty typu DTO czy listy obiektów typu DTO powinnny być umieszczane w modelu.
1. Dostosuj widoki JSP do użycia obiektów DTO, jeżeli jest to konieczne.

Po zrealizowaniu zadania przetestuj działanie stron, aby mieć pewność, że dane są prezentowane we właściwy sposób.

:hamster: _Coś tu na pewno przyniosę!_

---

### Zadanie 4.4. Wprowadzenia słownika kategorii do obsługi ogłoszeń

Chcemy rozbudować naszą warstwę funkcjonalną aplikacji, a więc dorzucimy nową encję reprezentującą kategorie ogłoszenia. Każde ogłoszenie powinno mieć pojedynczą kategorię. Kategoria powinna być wybierana przy utworzeniu ogłoszenia i nie może być później zmieniona (przy edycji). Nazwa kategorii powinna być unikalna, a sama kategoria poza nazwą powinna posiadać również opis. Kategorie powinniśmy również prezentować przy wyświetlaniu ogłoszeń.

Na etapie tego zadania zadbaj o to, aby między widokiem a kontrolerem wykorzystać obiekty w stylu DTO (`xxxDTO`, `xxxView` czy `xxxRequest`) zamiast encji.

Przy udostępnieniu listy możliwych kategorii do wyboru (przy tworzeniu ogłoszenia) możesz albo zadbać o umieszczenie takiego słownika w odpowiednim obiekcie DTO (np. do tworzenia ogłoszenia) albo wystawić osobną listę korzystając z metody kontrolera z adnotacją `@ModelAttribute`.

:hamster: _Coś tu na pewno przyniosę!_

---

### Zadanie 4.5. Wprowadzenie warstwy serwisów

Nasza architektura powoli dąży do docelowego rozwiązania. Najważniejszy jej element to warstwa serwisów. Zrealizujemy ją zachowując zasadę _loose coupling_, a więc z wykorzystaniem interfejsów. Dlatego każda funkcjonalność serwisu powinna powstać jako para interfejs + implementacja. Możesz przyjąć konwencję nazewniczą, że np. do obsługi zarządzania funcjonalnościami użytkownika stworzymy interfejs `UserService` i jego domyślną implementację `DefaultUserService` lub `UserServiceImpl`.

> Ja osobiście preferuje nazewnictwo typu `DefaultUserService`.

1. W bieżącej aplikacji możesz wydzielić co najmniej dwa serwisy:
   - `RegistrationService` do obsługi procesu rejestracji,
   - `UserService` do obsługi procesów użytkownika (publikowanie ogłoszenia, edytowanie ogłoszenia),
   - `AdvertService` do pobierania informacji o ogłoszeniach,
1. Przenieś logikę przetwarzania danych z kontrolerów do serwisów. Efekt tego kroku powinien sprawić, że w żadnym z kontrolerów nie będzie potrzebny nawet import jakiejkolwiek encji bądź repozytorium.
1. Zadbaj o to, aby przetwarzanie w serwisach było transakcyjne (adnotacja `@Transactional`).

Na koniec tego zadania uda Ci się uzyskać poprawną, wielowarstwową architekturę aplikacji, która spełnia zasady `SOLID`[https://blog.bitsrc.io/solid-principles-every-developer-should-know-b3bfa96bb688] (a przynajmniej ich część).

:hamster: _Coś tu na pewno przyniosę!_

---

### Zadanie 4.6. Wykorzystanie klasy bazowej encji

Zadanie to możemy zrealizować na dowolnym etapie, bo jest w pełni samodzielne. Usprawnimy wykorzystanie i tworzenie kolejnych encji, a także ulepszymy jakość danych dzięki wprowadzeniu encji bazowej.

1. Utwórz klasę `AbstractEntity` lub `BaseEntity`.
1. Klasę oznacz adnotacją `@MappedSuperclass`, dzięki czemu jej pola będą przenoszone do definicji encji dziedziczących.
1. W klasie stwórz pola:
   - `id` typu `Long`, automatycznie generowane z wykorzystaniem mechanizmu bazy danych,
   - `createdOn` typu `LocalDateTime`,
   - `updatedOn` typu `LocalDateTime`,
1. W klasie stwórz _gettery_ i _settery_ oraz metody `toString`, `hashCode` oraz `equals`. W metodzie `hashCode` i `equals` pamiętaj aby korzystać tylko z pola `id`.
1. Dodaj metodę z adnotacją `PrePersist`, w której ustawisz wartość pola `createdOn`, tak aby odpowiadała bieżącej dacie przy zapisie encji.
1. Dodaj metodę z adnotacją `PreUpdate`, w której ustawisz wartość pola `updatedOn`, tak aby odpowiadała bieżącej dacie przy aktualizacji encji.
1. Zmodyfikuj istniejące encje, aby rozszerzały encję bazową. Usuń z nich zbędne pola i metody. W implementacjach metod `toString` pamiętaj, aby odwoływać się również do implementacji z nadklasy.

Nasza encja bazowa ułatwi nam tworzenie kolejnych encji ale również poprawi jakoś danych. Co więcej, można ją dalej rozbudowywać, aby wnosiła jeszcze więcej mechanizmów.

:hamster: _Coś tu na pewno przyniosę!_

---

### Zadanie 4.7. Wprowadzenie ról użytkowników i implementacja funkcjonalności zarządzania kategoriami

W zadaniu wprowadzimy nową encję, która będzie reprezentowała rolę użytkownika. Encja ta będzie w relacji wiele-do-wielu z użytkownikiem. Zadanie realizujemy w czterech częściach.

**Cześć 1:** Utwórz encję `Role`, która będzie reprezentowałą role użytkownika. Powiąż encję `User` z encją `Role` relacją `ManyToMany`. Sama encja `Role` powinna posiadać pole reprezentujące unikalną nazwę roli.

**Część 2:** Dostosuj konfigurację bezpieczeństwa, aby role użytkownika były określane na podstawie faktycznych wpisów w bazie, opartych na nowo wprowadzonych tabelach (chodzi o konfigurację uwierzytelniania użytkownika przy pomocy `jdbc`).

**Część 3:** Zrealizuj funkcjonalność zarządzania (dodawanie, edytowanie, usuwanie) kategoriami, która będzie dostępna tylko dla zalogowanych użytkowników posiadających rolę `ADMIN` (`ROLE_ADMIN)`.

> Przy realizacji funkcjonalności zarządzania kategoriami pamiętaj, aby wykorzystać wszystkie elementy: widok, kontroler, DTO, serwis, repozytorium, encję. Logikę biznesową możesz umieścić w `AdmnistrationService`.

Zadanie to rozszerzy nasze możliwości wykorzystania _Spring Security_ ale również pozwoli przećwiczyć tworzenie funkcjonalności na wszystkich potrzebnych warstwach aplikacji.

:hamster: _Coś tu na pewno przyniosę!_

---

### Zadanie 4.8. Wykorzystanie biblioteki ModelMapper w projekcie

Żmudnym i niewdzięcznym procesem w naszej aplikacji jest konwertowanie danych między obiektami transferowymi, a encjami. Bardzo ułatwimy sobie zadanie wprowadzając do projektu bibliotekę ModelMapper.

1. W pierwszej kolejności zapoznaj się z podstawami użycia biblioteki [`ModelMapper`](http://modelmapper.org/getting-started/).
1. Stwórz w projekcie klasę `MappingConfiguration`, która będzie klasą konfiguracyjną dla aplikacji.
1. W klasie `MappingConfiguration` wystaw pojedynczą metodę typu `@Bean`, która będzie tworzyć nowy obiekt klasy `ModelMapper` z ustawioną _dosłowną/dokładną_ strategią konwertowania (`STRICT`).
1. Wstrzyknij do wszystkich serwisów zależność typu `ModelMapper` i użyj jej do konwersji między obiektami transferowymi a encjami, pozbywając się dotychczasowego, ręcznego kodu konwertującego.

Teraz możesz swobodnie rozwijać nowe funkcjonalności bez obciążania aplikacji tzw. _boilerplate code_ do konwertowania z obiektu na obiekt.

:hamster: _Coś tu na pewno przyniosę!_

---

### Zadanie 4.9. Wprowadzenie walidacji danych wchodzących

Obecnie nasza aplikacja nie zakłada żadnych mechanizmów walidacji. Wprowadzimy takie walidacje na wszystkich formularzach. Wykorzystamy standard `Bean Validation` oraz bibliotekę tagów `Spring Forms`.

1. Zmodyfikuj formularz rejestracji tak, aby wykorzystywał bibliotekę tagów `Spring Forms`.

   > Aby uzyskać prawidłowo działające formularze musisz dodawać do modelu atrybut o tej samej nazwie zarówno na metodzie `GET`, jak i na metodzie `POST`.
   
1. Wprowadź walidację na obiekcie transferowym wykorzystywanym do przesłania danych z formularza rejestracji:
   - nazwa użytkownika niech będzie obowiązkowa, nie pusta i składająca się z od `3` do `18` znaków,
   - hasło niech będzie obowiązkowe, nie puste i składające się z od `8` do `12` znaków.
1. Aktywuj walidację w obsłudze formularza rejestracji z wykorzystaniem adnotacji `@Valid`, `@ModelAttribute` oraz parametru typu `BindingResult`.
1. Skorzystaj z tagów `<form:errors path="xxx">`, aby w formularzu wyświetlać błędy walidacji obok odpowiednich pól formularza.
1. Przetestuj całość mechanizmu.

W przypadku poprawnego działania zrealizuj walidację na pozostałych formularzach:
- dodawanie ogłoszenia,
- edytowanie ogłoszenia,
- dodawanie kategorii,
- edytowanie kategorii.

Pryjmij założenia:
- ogłoszenie musi mieć tytuł nie pusty, o długości od `3` do `100` znaków,
- ogłoszenie musi mieć opis nie pusty, o długości co najmniej `3` znaków,
- kategoria musi mieć nazwę niepustą, o długości od `3` do `20` znaków,

Możesz spróbować wprowadzić również walidację tzw. _biznesową_, która sprawdza np. unikalność nazwy użytkownika czy unikalność nazwy kategorii.

> W tym wypadku będą Ci potrzebne własne adnotacje walidacyjne i własne klasy walidujące

:hamster: _Coś tu na pewno przyniosę!_

---

### Podsumowanie

Każde z zadań sprawia, że nasza aplikacja _rośnie_ w oczach innych programistów. Stąd nawet po jednym zrealizowanym zadaniu należy Ci się:

[Good job!](https://www.youtube.com/watch?v=S0UvJZmGTsk)

... a jeżeli udało Ci się zrealizować wszystkie, to masz prawo do zapętlenia gratulacji i oglądania cały dzień!

---

[Wstecz](../module_3/README.md) | Dalej