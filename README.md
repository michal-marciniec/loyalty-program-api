# Loyalty Program API
REST API that allows you to incorporate a customizable loyalty rewards program into your own application.

The goal of this project is to expose a developer-friendly API via which an organization may define:
- bonuses available for organization members for a specific amount of points
- rules for points exchange between members
- automatically rewarding best members in a specific timespan (rankings)
- periodically resetting pools of points available for members
- process of reward claim realization
- ... many many more ...

## Developer's guide
### Tech Stack
##### Source
- Java 8
- Spring Boot Web
- Spring Data JPA
- Spring Security
- Hibernate
- Flyway migrations
- QueryDSL
- ModelMapper
- Lombok
- Jackson
- Swagger
- Gradle

##### Tests
- Kotlin
- Spek
- Mockito
- JUnit

### Build & run
The application uses gradle as a build tool. To build and run go to project directory and simply execute:
```
./gradlew build
./gradlew bootRun
```
It can be then accessed at `localhost:8999`.

API specification is available at `http:localhost:8999/swagger-ui.html`
