# Loyalty Program API

### Prerequisites

To build and run the application following are needed to be installed:
- JDK 8
- Gradle

After installation you need to download proper gradle wrapper version, which is defined in build.gradle. It can be accomplished by running:

```
gradle wrapper
```

in project directory.

### Build and run

The application uses gradle as a build tool. To build and run go to project directory and simply execute:

```
./gradlew build -x test
./gradlew bootRun
```
It can be then accessed at localhost:8999

### Running the tests

Tests can be run using gradle build tool by typing:

```
./gradlew test
```
