# Jobsy — Reactive Microservices Architecture

> Academic project to practice reactive microservices with Spring Boot WebFlux and Clean/Hexagonal Architecture.


## Table of Contents

- [Overview](#overview)
- [Learning objectives](#learning-objectives)
- [Architecture & repository layout](#architecture--repository-layout)
- [Technology stack](#technology-stack)
- [Prerequisites](#prerequisites)
- [Quickstart (Docker Compose)](#quickstart-docker-compose)
- [Run single service locally (ms-users)](#run-single-service-locally-ms-users)
- [Profiles and useful notes](#profiles-and-useful-notes)
  - [Disable security for tests](#disable-security-for-tests)
  - [UUID handling with R2DBC](#uuid-handling-with-r2dbc)
- [Testing](#testing)
- [Development tips](#development-tips)
- [Contributing](#contributing)
- [Author](#author)


## Overview

This repository contains small reactive microservices (educational). Each service follows Clean/Hexagonal principles: a framework-agnostic domain, application services, and adapters for inbound/outbound concerns.

The goal is learning: reactive programming (WebFlux), reactive persistence (R2DBC), service decomposition, and containerization.


## Learning objectives

- Design focused microservices with single responsibility.
- Apply Clean / Hexagonal architecture inside each microservice.
- Use Spring WebFlux and Project Reactor (Mono / Flux).
- Keep domain layer independent from frameworks.
- Use reactive persistence with R2DBC and handle UUID identifiers.
- Use Eureka for service discovery and Docker Compose for local orchestration.


## Architecture & repository layout

Top-level structure (trimmed):

```
textjobsy-microservices/
├── README.md
├── docker-compose.yml
├── discovery-server/    # Eureka
├── ms-users/            # Users microservice (current focus)
├── ms-jobs/             # Jobs microservice (future)
└── ...
```

Each microservice contains its own `Dockerfile`, `README.md` and `src/`.


## Technology stack

- Java 17+
- Spring Boot 3.x + WebFlux
- Project Reactor
- Spring Data R2DBC (reactive persistence)
- MySQL 8+ (R2DBC driver)
- Netflix Eureka (service discovery)
- Maven (wrapper included)
- Docker, Docker Compose


## Prerequisites

- Java 17+ installed
- Docker & Docker Compose (v2 recommended)
- Git


## Quickstart (Docker Compose)

Start the whole system (Eureka + services) from the repository root.

Windows (cmd.exe):

```bat
REM from project root
docker compose up --build
```

Unix / PowerShell:

```bash
docker compose up --build
```

What this starts:
- Eureka discovery server
- All configured microservices (each will register with Eureka)

To stop and remove containers:

```bash
docker compose down
```


## Run single service locally (ms-users)

From the `ms-users` folder you can run the service without Docker.

Windows (cmd.exe):

```bat
cd ms-users
mvnw.cmd spring-boot:run
REM or: .\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev
```

Unix / macOS:

```bash
cd ms-users
./mvnw spring-boot:run
# or with profile
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```


## Profiles and useful notes

### Disable security for tests

Spring Boot does not support a `security.enabled` property. To completely disable Spring Security auto-configuration in a profile (e.g. `test`), exclude the security auto-configs or provide a test Security configuration. Example for `application-test.yml`:

```yaml
spring:
  autoconfigure:
    exclude:
      - org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
      - org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration
      - org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration
```

This is useful for integration tests that shouldn't require authentication.


### UUID handling with R2DBC

The MySQL R2DBC driver may not provide a codec for `java.util.UUID` out of the box (error: "Cannot encode class java.util.UUID"). Recommended approaches:

- Store UUID as a `CHAR(36)` / `VARCHAR(36)` in DB and register converters that map UUID <-> String (recommended to keep domain UUID type).
- Convert UUID to String manually when binding statements (quick workaround).

If you need the converters, see the project's adapters: `adapter/out/config/R2dbcConfig.java` and `UUIDToStringConverter` / `StringToUUIDConverter`.


## Testing

Unit tests: run with Maven

```bash
mvn test
```

Integration tests (profile `test` if configured):

```bash
./mvnw test -Dspring.profiles.active=test
```


## Development tips

- Keep domain classes free of Spring annotations.
- Bind application services in a `@Configuration` class (e.g. `adapter/in/config/UserApplicationConfig.java`) to preserve separation and make tests easier.
- For Java 8 compatibility avoid `List.of(...)` — use `Arrays.asList(...)` or `Collections.*`.
- When adding R2DBC database migrations, ensure `schema.sql` matches column types (e.g. `CHAR(36)` for UUID stored as string).


## Contributing

1. Fork the repo
2. Create a branch feature/xxx
3. Implement and add tests
4. Open a PR with a clear description


## Author

Esteban Cano — Academic project for practicing reactive microservices and software architecture.


---

If you want, I can:
- add badges and a short architecture diagram (ASCII or link to an image),
- produce a short README inside `ms-users/` with service-specific run examples,
- or convert the quick commands into a `Makefile` or `scripts/` folder for Windows/Unix.

Tell me which of these extras you want and I will apply them.
