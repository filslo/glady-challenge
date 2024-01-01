# Explanation of implementation choices

## Build / prerequisites

For this challenge, I used Java 11 and Maven Wrapper.
Java 11 is currently the latest long-term support (LTS) version of Java.
[Maven Wrapper](https://maven.apache.org/wrapper/) is a tool that allows you to use Maven without having to install it on your system. 
It provides a consistent build environment across different machines and ensures that everyone working on the project is using the same version of Maven.

## Framework

For this challenge, I chose to use Spring Boot as the framework.
[Spring Boot](https://spring.io/projects/spring-boot/) is a Java framework that makes it easy to create web applications by providing ready-to-use features 
such as automatic configuration, dependency management, and integrated web servers.

## Authentication

I chose not to implement an authentication mechanism to simplify the application.
This means that anyone can access the application services without having to authenticate.
In a production environment, it would be necessary to implement an authentication mechanism to protect sensitive data.

## Persistence layer

I chose not to use a persistence layer to store data. Instead, I opted for in-memory storage.
This means that all data is stored in RAM and is lost when the application is stopped.
This approach is simple and quick to set up, but it is not suitable for a production environment where data needs to be persistent.
In a production environment, it would be necessary to use a database.

## Swagger UI

I added Swagger UI to the application to facilitate documentation and use of the services.
Swagger UI is a user interface that allows you to visualize and test the application services.

## Possible improvements

To improve the application, it would be possible to add:
* integration tests
* and contract testing.

Integration tests would test the application as a whole, verifying that all components work correctly together.
Contract testing would test the interactions between the application and the clients that use it, 
verifying that the contracts between the two are respected. 
These two types of tests would ensure the quality and reliability of the application in a production environment.
