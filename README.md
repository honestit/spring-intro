# Intro Into Spring

Repozytorium warsztatowe z zadaniami wprowadzającymi do framework'u Spring

---

## Nota prawna

Drogi użytkowniku. Ze względu na ochronę wartości intelektualnej zachęcam Cię, abyś na wstępie zapoznał się z poniższą notą prawną dotyczącą repozytorium. Uzyskałeś dostęp do repozytorium i tym samym poniższe zapisy w całości Cię dotyczą. Proszę Cię o współpracę w ochronie tej wartości i przestrzeganie niżej opisanych zasad. Dziękuję.

> Repozytorium, jego treść i zawartość podlegają ochronie prawnej. Wykorzystywanie materiałów zawartych w repozytorium może odbywać się tylko za zgodą autora.

> Repozytorium udostępniane jest wybranym osobom i tylko przez te osoby może być wykorzystywane. Dalsze rozpowszechnianie i/lub kopiowanie repozytorium bez zgody autora, w całości lub we fragmentach, jest zabronione.

> Autorem i właścicielem repozytorium jest Michał Kupisiński Honest IT Consulting. 

> Repozytorium oraz każda jego kopia (fork) muszą zachować powyższą notę prawną.

---

## Przygotowanie repozytorium

1. Repozytorium należy w pierwszej kolejności sklonować na swój komputer przy pomocy polecenia `git clone`.
1. Projekt, który jest opisany w repozytorium należy zrealizować w katalogu `project`.
1. Każdy krok z poszczególnych zadań powinien kończyć się wykonaniem `git commit` (można częściej).

> Jeżeli posiadasz konto na `github.com`, to możesz wykonać `fork` tego repozytorium i również wrzucać swoje zmiany `git push` do swojego `fork'a`. Pamiętaj tylko, aby wcześniej sklonować właśnie swój `fork` a nie repozytorium główne.

---

## Wprowadzenie

W ramach warsztatów zrealizujemy wspólnie prostą aplikację bazującą na Spring Framework. Będzie to serwis webowy umożliwiający użytkownikom publikacje ogłoszeń. 

Aplikacja będzie zawierała następujący zestaw funkcjonalności:

1. Obsługa użytkowników
   1. Rejestracja nowych użytkowników
   1. Logowanie
   1. Wylogowanie
   
1. Obsługa ogłoszeń
   1. Lista ostatnio dodanych ogłoszeń na stronie głównej
   1. Lista wszystkich ogłoszeń zalogowanego użytkownika
   1. Lista wszystkich ogłoszeń wybranego użytkownika
   
W trakcie prac nad tą prostą aplikacją wprowadzimy sobie podstawowe zagadnienia i techniki związane z:
1. **Spring Core/Context** - do obsługi wstrzykiwania zależności (IoC/DI),
1. **Spring MVC** - do obsługi żądań protokołu HTTP, w architekturze MVC,
1. **Spring Boot** - do szybkiego startu i automatycznej konfiguracji projektu,
1. **Spring Data JPA** - do integracji ze standardem JPA i obsługi warstwy bazodanowej,
1. **JSP** - do generowania dynamicznych widoków.

---

## Moduł 1. Podstawowa konfiguracja projektu

W pierwszej kolejności zajmiemy się przygotowaniem projektu do dalszych prac. Musimy zadbać o to, aby w naszym projekcie:
- pojawiły się wszystkie zależności,
- pojawiły się wszystkie wymagane konfiguracje.

[Rozpocznij pracę z modułem](module_1/README.md)

---

## Moduł 2. Proces rejestracji i logowania

Mamy podstawową konfigurację projektu i udało nam się go uruchomić. Będziemy realizować teraz kolejne funkcjonalności, a zaczniemy od rejestracji użytkownika oraz procesu logowania/wylogowania.

Potrzebować będziemy:
- klasy reprezentującej użytkownika, np. `User`,
- klasy kontrolera Spring MVC obsługującej proces rejestracji, np. `RegistrationController`,
- strony JSP z widokiem formularza rejestracji,
- strony JSP z widokiem formularza logowania i jej obsługi w kontrolerze.

[Rozpocznij pracę z modułem](module_2/README.md)

---

## Moduł 3. Dodawanie ogłoszeń i lista ogłoszeń

W ramach zadań zrealizujemy dalszy zestaw funkcjonalności związany z obsługą ogłoszeń użytkowników. Zaczniemy od opcji dodania ogłoszenia oraz listy wszystkich ogłoszeń. Następnie przejdziemy do listy ogłoszeń per użytkownik oraz list ogłoszeń zalogowanego użytkownika. W ramach zadań dodatkowych możemy również wprowadzić edycję ogłoszenia oraz opcję jego usunięcia.

[Rozpocznij pracę z modułem](module_3/README.md)

---

### Dziękuję za wspólną pracę ;)
