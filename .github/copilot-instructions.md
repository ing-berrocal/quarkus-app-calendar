# Copilot Instructions

## Build and test

Run Maven commands from the repository root:

```sh
./mvnw clean package
./mvnw test
./mvnw test -Dtest=ScheduleAPITest
./mvnw test -Dtest=ScheduleAPITest#testGetSchedule_success
./mvnw quarkus:dev
```

On Windows PowerShell, use `.\mvnw.cmd` in place of `./mvnw`. Tests use JUnit 5 with
Quarkus tests/component tests; endpoint tests use REST Assured and service tests can
mock CDI dependencies with Mockito. The POM does not configure a dedicated lint or
formatting plugin.

## Architecture

- Jakarta REST resources in `com.calendario.app.controller` expose the API. Request
  payloads are represented by DTOs under `com.calendario.app.model.request`; resources
  translate these to application/domain values and delegate to managers.
- Managers in `com.calendario.app.service` coordinate business operations. For example,
  `AppointmentManager` validates customer/service references and availability before
  delegating writes to `AppointmentRepository`.
- Persistence is in `com.calendario.persistence`. Panache repositories operate on JPA
  entities; repository methods map between entities and application/domain models.
  Appointment calendar reads also use `AppointmentViewEntity`, mapped to the database
  view `public.view_appointment`, with its named queries declared on that entity.
- Scheduling crosses these layers: `ScheduleManager` combines service duration and
  instances (via `ServiceBeanImplementation`) with appointment availability from
  `AppointmentRepository`. The schedule start is configured by
  `schedule.config.hour.init.minutes`.
- The repository contains both `com.calendario.app` and older top-level
  `com.calendario` code. Follow the existing package and call path for the feature being
  changed; check usages before moving or consolidating code.

## Project-specific conventions

- Keep HTTP payload types separate from persistence entities. Use the request/response
  DTOs and model records already used by the relevant endpoint; apply Jakarta validation
  with `@Valid` and constraints on request DTOs.
- Keep business decisions in managers, persistence queries/transactions in repositories,
  and map errors through the existing exception mappers in
  `com.calendario.app.controller.exceptionmapper`.
- Preserve existing endpoint paths when modifying an API. Some established routes use
  paths such as `/appointment/add` and `/appointment/edit/{id}`; use resource-oriented
  paths for new endpoints without silently changing existing clients' routes.
- CDI beans are generally `@ApplicationScoped`; inject collaborators with CDI and put
  write operations inside transactional persistence methods.
- PostgreSQL and JWT settings are supplied through Quarkus configuration and environment
  placeholders. Keep environment-specific values out of Java source and inspect the
  relevant configuration before changing startup or authentication behavior.

See `README.md` for Quarkus packaging and native-build details, and `INSTRUCTIONS.md` for
the broader project conventions.
