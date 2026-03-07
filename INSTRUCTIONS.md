# Instrucciones para el Proyecto Quarkus App Calendar

## Descripción del Proyecto
Este proyecto es una aplicación basada en Quarkus para la gestión de eventos y citas en un calendario. Utiliza Java 21 y sigue una arquitectura modular con capas bien definidas para controladores, servicios, dominio y persistencia. La base de datos utilizada es PostgreSQL, y el proyecto está configurado para exponer una API REST para operaciones CRUD.

---

## Estructura del Proyecto

- **Controladores:**
  - Ubicación: `src/main/java/com/calendario/app/controller/`
  - Contiene las clases que manejan las solicitudes HTTP y exponen los endpoints REST.
  - Ejemplo: `AppointmentAPI.java`, `CustomerAPI.java`.

- **Servicios:**
  - Ubicación: `src/main/java/com/calendario/app/service/`
  - Contiene la lógica de negocio y se inyectan en los controladores.
  - Ejemplo: `AppointmentManager`.

- **Modelos de Dominio:**
  - Ubicación: `src/main/java/com/calendario/app/model/`
  - Representan las entidades del dominio y se utilizan para la persistencia.

- **Persistencia:**
  - Ubicación: `src/main/java/com/calendario/persistence/`
  - Contiene las clases relacionadas con la interacción con la base de datos utilizando JPA y Panache.

- **Recursos:**
  - Ubicación: `src/main/resources/`
  - Archivos de configuración como `application.properties` y scripts SQL de inicialización.

---

## Herramientas y Dependencias

- **Quarkus:** Framework principal para la aplicación.
- **PostgreSQL:** Base de datos relacional utilizada.
- **Maven:** Herramienta de construcción y gestión de dependencias.
- **JUnit y Mockito:** Para pruebas unitarias e integración.

---

## Flujos de Trabajo

### Construcción del Proyecto
1. Ejecutar `./mvnw clean package` para construir el proyecto.
2. Los artefactos generados estarán en el directorio `target/`.

### Modo de Desarrollo
1. Ejecutar `./mvnw quarkus:dev` para iniciar el servidor en modo de desarrollo.
2. Acceder a la interfaz Dev UI en `http://localhost:8080/q/dev/`.

### Ejecución
1. Ejecutar la aplicación empaquetada con `java -jar target/quarkus-app/quarkus-run.jar`.

### Construcción Nativa
1. Ejecutar `./mvnw package -Dnative` para construir un ejecutable nativo.
2. Usar `-Dquarkus.native.container-build=true` si no se tiene GraalVM instalado localmente.

### Pruebas
1. Colocar las pruebas en `src/test/java/`.
2. Ejecutar `./mvnw test` para correr las pruebas.

---

## Convenciones del Proyecto

- **Nombres:**
  - Clases/Interfaces/Enums: PascalCase.
  - Métodos/Variables: camelCase.
  - Constantes: ALL_CAPS.

- **Estilo de Código:**
  - Usar espacios en lugar de tabs.
  - Mantener las líneas cortas (<120 caracteres).
  - Comentar en inglés y usar Javadoc para APIs públicas.

- **Manejo de Errores:**
  - Retornar códigos HTTP apropiados (e.g., 201 para creación, 400 para entrada inválida).
  - Usar mapeadores de excepciones de Quarkus para respuestas consistentes.

- **Seguridad:**
  - Proteger los endpoints según sea necesario (e.g., `@RolesAllowed`).

- **Base de Datos:**
  - Usar entidades JPA para la persistencia.
  - Scripts SQL de inicialización en `src/main/resources/import.sql`.

---

## Ejemplos

- **Estructura de un Endpoint REST:** Ver `AppointmentAPI.java`.
- **Lógica de Negocio:** Ver `AppointmentManager.java`.
- **Pruebas:** Ver `AgendaServicioTest.java` para ejemplos de pruebas unitarias.

---

## Notas Adicionales

- Seguir las convenciones RESTful para nuevos endpoints.
- Mantener la lógica de negocio fuera de los controladores; usar clases de servicio.
- Actualizar la documentación y configuración al agregar nuevas funcionalidades.