# Hotel Rating Application based on Spring Boot 3.0 with JWT Implementation
The project enables users to submit their reviews of hotels. The database includes the data of all application users, including the 
admin `(username: admin, password: password)` and the moderator `(username: moderator, password: password)`. Each newly registered user is assigned `user` permissions. Upon logging in, the application generates a token that allows access to individual endpoints.



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

- Clone the repository: git clone https://github.com/mtlon/HotelReviewApplication.git
- Navigate to the project directory: cd HotelReviewApplication
- Configure the database by setting up the PostgreSQL database:
  - Start the PostgreSQL server.
  - Create a new database named `hotel` by running the command: createdb hotel
  - Set the login credentials for the database by adding the following lines to the application.properties file:
  ```
    spring.datasource.url=jdbc:postgresql://localhost:5432/hotel
    spring.datasource.username=postgres
    spring.datasource.password=password
    spring.datasource.driver-class-name=org.postgresql.Driver
  ```
    - Be sure to replace the username and password variables with your own PostgreSQL credentials.
- Build the project: mvn clean install
- Run the project: mvn spring-boot:run

-> The application will be available at http://localhost:8080.
