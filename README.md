# Spring Boot Hotel Application
This repository serves as an educational resource for practicing, studying, and testing various programming concepts. <br>
Please note that it is not intended for real-world applications but instead emulates a simple Hotel Reservation app.

## Basic requirements and instructions
The application is developed using the following technologies:

- Java 17
- Spring Boot
- Gradle
- Visual Studio Code (VS Code)

### Running the Application
1. Open a terminal
2. Navigate to the project root directory
3. Run the following command
``` bash
./gradlew bootRun
```
This will start the SpringBoot application.

### Runing Unit Tests
To execute unit tests, use the following command: 
``` bash
./gradlew test
```


### Debug the Application
Debugging the application is easy with VS Code:
1. Open the project in VS Code.
2. Set breakpoints in your code.
3. Click the IDE's debugging button to start debugging, and the application will pause at your breakpoints.

## Running the application with Docker
To build and run the Spring Boot application using Docker, follow these steps:

1. Build the Application:
   
Before creating the Docker image, build the Spring Boot application by running the following command:
``` bash
./gradlew bootJar
```
2. Create the Docker Image:
   
Once the application is built, create a Docker image using the following command:
``` bash
docker build -t hotel .
```
3. Run the Docker Container:
   
After the Docker image is built, run a container using the following command:
``` bash
docker run -p 8080:8080 hotel
```
This will start the container and map port 8080 from the container to port 8080 on your local machine.

Note: Keep in mind that since a local database is used, the data will not be persisted and will be lost when the Docker containers are destroyed.

By following these steps, you should have the Spring Boot application up and running in a Docker container. Access your application by navigating to http://localhost:8080 in your web browser or making API requests.

## TO DO
