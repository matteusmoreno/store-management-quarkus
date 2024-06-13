# store-management

## Technologies
[![Java](https://img.shields.io/badge/Java-17-blue)](https://openjdk.java.net/projects/jdk/17/)
[![Quarkus](https://img.shields.io/badge/Quarkus-3.10.0-red)](https://quarkus.io/)
[![Hibernate ORM](https://img.shields.io/badge/Hibernate%20ORM-5.6.0-green)](https://hibernate.org/orm/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)](https://dev.mysql.com/doc/)
[![Flyway](https://img.shields.io/badge/Flyway-10.10.0-orange)](https://flywaydb.org/)
[![JUnit 5](https://img.shields.io/badge/JUnit-5-yellow)](https://junit.org/junit5/)
[![Rest Assured](https://img.shields.io/badge/Rest%20Assured-4.4.0-lightgrey)](https://rest-assured.io/)
[![Lombok](https://img.shields.io/badge/Lombok-1.18.32-blueviolet)](https://projectlombok.org/)
[![Jacoco](https://img.shields.io/badge/Jacoco-0.8.10-brightgreen)](https://www.eclemma.org/jacoco/)
[![MongoDB Panache](https://img.shields.io/badge/MongoDB%20Panache-3.10.0-green)](https://quarkus.io/guides/mongodb-panache)
[![Quarkus Mailer](https://img.shields.io/badge/Quarkus%20Mailer-3.10.0-blue)](https://quarkus.io/guides/mailer)
[![Quarkus OIDC](https://img.shields.io/badge/Quarkus%20OIDC-3.10.0-red)](https://quarkus.io/guides/security-openid-connect)
[![Quarkus Security](https://img.shields.io/badge/Quarkus%20Security-3.10.0-yellow)](https://quarkus.io/guides/security)
[![Google API Client](https://img.shields.io/badge/Google%20API%20Client-1.32.1-blue)](https://developers.google.com/api-client-library/java)


This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/store-management-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Related Guides

- REST ([guide](https://quarkus.io/guides/rest)): A Jakarta REST implementation utilizing build time processing and Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it.
- Hibernate ORM ([guide](https://quarkus.io/guides/hibernate-orm)): Define your persistent model with Hibernate ORM and Jakarta Persistence
- Flyway ([guide](https://quarkus.io/guides/flyway)): Handle your database schema migrations
- REST Client ([guide](https://quarkus.io/guides/rest-client)): Call REST services
- SmallRye OpenAPI ([guide](https://quarkus.io/guides/openapi-swaggerui)): Document your REST APIs with OpenAPI - comes with Swagger UI
- JDBC Driver - MySQL ([guide](https://quarkus.io/guides/datasource)): Connect to the MySQL database via JDBC

## Provided Code

### Hibernate ORM

Create your first JPA entity

[Related guide section...](https://quarkus.io/guides/hibernate-orm)



### REST Client

Invoke different services through REST with JSON

[Related guide section...](https://quarkus.io/guides/rest-client)

### REST

Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
