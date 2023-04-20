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

## Installation
To get started with this project, you will need to have the following installed on your local machine:
- [OpenJDK 17+](https://www.oracle.com/java/technologies/javase/jdk18-archive-downloads.html)
- [IntelliJ IDEA Ultimate](https://www.jetbrains.com/idea/)
- [Maven 3+](https://maven.apache.org/)
- [PostgreSQL](https://www.postgresql.org/download/)

## Setup
To build and run the project, follow these steps:

- Clone the repository: `git clone https://github.com/mtlon/HotelReviewApplication.git`
- Navigate to the project directory: cd HotelReviewApplication
- Build the project: mvn clean install
- Run the project: mvn spring-boot:run

-> The application will be available at http://localhost:8080.
