# Hotel Careaga API

API simple para gestión de hotel con Quarkus (Java 17): habitaciones, reservas, servicios extra y consumos. Incluye validaciones, manejo de errores y documentación OpenAPI.

**Stack**
- Quarkus REST + Jackson, Hibernate ORM con Panache, Bean Validation, SmallRye OpenAPI
- PostgreSQL (Docker Compose)

**Estructura**
- `src/main/java/py/com/hotelcareaga/entity` (JPA)
- `src/main/java/py/com/hotelcareaga/dto` (DTOs con Bean Validation)
- `src/main/java/py/com/hotelcareaga/repository` (Panache)
- `src/main/java/py/com/hotelcareaga/service` (lógica/tx)
- `src/main/java/py/com/hotelcareaga/resource` (REST + OpenAPI)

## Cómo correr
- Base de datos: `docker-compose up -d`
- App en dev: `./mvnw quarkus:dev`
- Swagger UI: `http://localhost:8080/swagger-ui`
- Dev UI: `http://localhost:8080/q/dev/`

Config DB en `src/main/resources/application.properties`. Para demo se usa `drop-and-create` (solo dev).

## Endpoints principales

Habitaciones (`/habitaciones`)
- GET `/` listar | GET `/{id}` por id
- POST `/` crear | PUT `/{id}` actualizar | DELETE `/{id}` borrar

Reservas (`/reservas`)
- GET `/` listar (opcional `?estado=PENDIENTE|EN_CURSO|FINALIZADA|CANCELADA`)
- GET `/{id}` por id | POST `/` crear | PUT `/{id}` actualizar | DELETE `/{id}` borrar
- Acciones: PUT `/{id}/checkin`, PUT `/{id}/checkout`, PUT `/{id}/cancelar`

Servicios Extra (`/servicios-extra`)
- GET `/`, GET `/{id}`, POST `/`, PUT `/{id}`, DELETE `/{id}`

Consumos (`/consumos`)
- GET `/`, GET `/{id}`, POST `/`, PUT `/{id}`, DELETE `/{id}`

## Validaciones y errores
- Bean Validation en DTOs (ej.: `@NotBlank`, `@Email`, `@Min`)
- `@Valid` en `POST/PUT`
- Mappers globales: 400 por validación/estado inválido y cuerpo JSON con detalle

## Pruebas (tests)

Escribe tests con `@QuarkusTest` y RestAssured.

Ejemplo (crear habitación):
```java
// src/test/java/py/com/hotelcareaga/HabitacionResourceTest.java
package py.com.hotelcareaga;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class HabitacionResourceTest {
  @Test
  void crearHabitacion() {
    given()
      .contentType("application/json")
      .body("{\"numero\":\"101\",\"tipo\":\"SIMPLE\",\"precio\":150000,\"capacidad\":2}")
    .when()
      .post("/habitaciones")
    .then()
      .statusCode(201)
      .body("numero", is("101"));
  }
}
```

Comandos:
- Ejecutar tests: `./mvnw test`
- Pruebas + verificación: `./mvnw verify`

Sugerencias:
- Usar `@TestMethodOrder` y limpiar datos si cambias el esquema.
- Para endpoints que leen DB, crear datos con `POST` en `@BeforeEach`.

## Demo rápida
- Levantar DB y app (ver “Cómo correr”).
- Probar con `api-tests.http` o via Swagger UI.

## Notas
- Evitar usar credenciales en claro para producción.
- Considerar migraciones (Flyway/Liquibase) si escalás el proyecto.

