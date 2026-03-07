# Project general coding guidelines

## Project Overview
This is a Quarkus-based Java 21 application. It exposes a REST API for CRUD operations on appointments, using JPA/Hibernate for persistence and PostgreSQL as the database. The codebase is modular, with clear separation between controller, service, domain, and persistence layers.

## Key Architecture & Patterns
- **REST API:** All endpoints are under `src/main/java/com/calendario/app/controller/`, e.g., `AppointmentAPI.java`. Endpoints use Jakarta REST annotations and follow RESTful conventions, but some legacy endpoints may use non-standard paths (e.g., `/add`, `/edit/{id}`) – prefer standard REST resource naming for new code.
- **Service Layer:** Business logic is in `src/main/java/com/calendario/app/service/` (e.g., `AppointmentManager`). Services are injected into controllers using `@Inject`.
- **Domain & Persistence:** Domain models are in `com/calendario/app/model/` and related subfolders. Persistence logic is handled via JPA repositories and Panache where possible.
- **DTOs:** Use DTOs for API requests/responses (e.g., `AppointmentRequest`, `AppointmentView`).
- **Validation:** Use `@Valid` for request validation. Handle validation errors with appropriate HTTP responses.
- **Configuration:** App config is in `src/main/resources/application.properties` and `dev.application.properties`. Avoid hardcoding values in code.

## Developer Workflows
- **Build:** Use `./mvnw clean package` to build. Artifacts are in `target/`.
- **Dev Mode:** Use `./mvnw quarkus:dev` for hot-reload and Dev UI at http://localhost:8080/q/dev/.
- **Run:** Run packaged app with `java -jar target/quarkus-app/quarkus-run.jar`.
- **Native Build:** Use `./mvnw package -Dnative` (optionally with `-Dquarkus.native.container-build=true`).
- **Tests:** Place unit/integration tests in `src/test/java/`. Run with `./mvnw test`. Use JUnit and Mockito for testing.

## Project Conventions
- **Naming:**
	- Classes/Interfaces/Enums: PascalCase
	- Methods/Variables: camelCase
	- Constants: ALL_CAPS
- **Code Style:**
	- Use spaces, not tabs. Keep lines short (<120 chars).
	- Comment in English. Use Javadoc for public APIs.
- **Error Handling:**
	- Return appropriate HTTP status codes (e.g., 201 for creation, 400 for bad input).
	- Use Quarkus exception mappers for consistent error responses.
- **Security:**
	- Secure endpoints as needed (e.g., `@RolesAllowed`).
- **Database:**
	- Use JPA entities for persistence. SQL scripts for init in `src/main/resources/import.sql`.

## Integration Points
- **PostgreSQL:** Configured via JDBC in `application.properties`.
- **Quarkus Extensions:** Uses RESTEasy, Hibernate ORM, Panache, and JDBC drivers.

## Examples
- See `AppointmentAPI.java` for REST endpoint structure and DTO usage.
- See `AppointmentManager.java` for service logic patterns.
- See `src/test/java/com/calendario/AgendaServicioTest.java` for test structure.

## Additional Notes
- For new endpoints, follow RESTful conventions and use DTOs for all API boundaries.
- Keep business logic out of controllers; use service classes.
- Update documentation and configuration when adding new features.

---
If any conventions or workflows are unclear, check the `README.md` or ask for clarification.