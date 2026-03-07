# quarkus-app-calendar

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.


## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

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

podman build -f src\main\docker\Dockerfile.jvm . --tag docker.io/dberrocal/document-back:v0.1 
```

You can then execute your native executable with: `./target/quarkus-app-calendar-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Related Guides

- REST ([guide](https://quarkus.io/guides/rest)): A Jakarta REST implementation utilizing build time processing and Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it.
- Hibernate ORM ([guide](https://quarkus.io/guides/hibernate-orm)): Define your persistent model with Hibernate ORM and Jakarta Persistence
- Hibernate Validator ([guide](https://quarkus.io/guides/validation)): Validate object properties (field, getter) and method parameters for your beans (REST, CDI, Jakarta Persistence)
- Hibernate ORM with Panache ([guide](https://quarkus.io/guides/hibernate-orm-panache)): Simplify your persistence code for Hibernate ORM via the active record or the repository pattern
- Java Flight Recorder (JFR) ([guide](https://quarkus.io/guides/jfr)): Monitor your applications with Java Flight Recorder
- JDBC Driver - PostgreSQL ([guide](https://quarkus.io/guides/datasource)): Connect to the PostgreSQL database via JDBC

## Provided Code

### Hibernate ORM

Create your first JPA entity

[Related guide section...](https://quarkus.io/guides/hibernate-orm)

[Related Hibernate with Panache section...](https://quarkus.io/guides/hibernate-orm-panache)


### REST

Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)


## Genera unas intrucciones para copoilot, que ayuden a entender el proyecto y a generar codigo util para el proyecto
### Proyecto: Quarkus-Calendar
### Descripcion: Proyecto para gestionar eventos en un calendario usando Quarkus
### Objetivo: Facilitar la gestion de eventos y recordatorios en un calendario personal o compartido
### Funcionalidades Principales: Crear, editar, eliminar y visualizar eventos en un calendario
### Tecnologias: Quarkus, Java, REST API, JPA, Hibernate, PostgreSQL
### Dependencias Clave: Quarkus Extensions (RESTEasy, Hibernate ORM, JDBC Drivers), JUnit, Mockito
### Estructura del Codigo:
- src/main/java: Codigo fuente principal
- src/main/resources: Recursos y configuraciones
- src/test/java: Pruebas unitarias e integracion
- pom.xml: Archivo de gestion de dependencias y build
### Buenas Practicas: 
- Seguir principios SOLID
- Escribir codigo limpio y mantenible
- Documentar funciones y clases
- Nombres de las clses, funciones y comentarios en ingles
- Usar control de versiones (Git)

## Naming Conventions
- Use PascalCase for class names, interfaces and enums
- Use camelCase for variables, functions, and methods
- Use ALL_CAPS for constants

## Code Quality
- Use meaningful variable and function names that clearly describe their purpose
- Include helpful comments for complex logic
- Add error handling for user inputs and API calls

### Estilo de Codigo: Seguir convenciones de nomenclatura de Java, usar espacios en lugar de tabulaciones, mantener lineas de codigo cortas
