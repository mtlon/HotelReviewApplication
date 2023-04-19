# Hotel Rating Application based on Spring Boot 3.0 with JWT Implementation
Projekt umożliwia użytkownikom dodawanie opini o hotelach. 
Każdy zarejestrowany użytkownik ma uprawnienia użytkownika, a admin i moderator posiadają dodatkowe uprawnienia. Projekt oparty jest na JWT token, który umożliwia korzystanie z API. Przykładowy endpoint to /hotel, który umożliwia dodanie nowego hotelu (dla użytkowników z rolą admin lub moderator).

## Technology
- Java 8
- Spring Security
- Spring Boot 3.0
- JSON Web Tokens (JWT)
- Hibernate
- PostgreSQL
- BCrypt
- Maven
