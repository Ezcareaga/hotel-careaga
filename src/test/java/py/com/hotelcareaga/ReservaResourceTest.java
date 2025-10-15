package py.com.hotelcareaga;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class ReservaResourceTest {

    @Test
    void crearYObtenerReserva() {
        // Crear habitaciÃ³n base
        Integer habitacionId =
            given()
                .contentType("application/json")
                .body("{\"numero\":\"T201\",\"tipo\":\"SUITE\",\"precio\":250000,\"capacidad\":4}")
            .when()
                .post("/habitaciones")
            .then()
                .statusCode(201)
                .extract().path("id");

        // Crear reserva
        String body = String.format("{\"idHabitacion\":%d,\"fechaInicio\":\"2025-11-01\",\"fechaFin\":\"2025-11-05\",\"nombreCliente\":\"Ana Perez\",\"emailCliente\":\"ana@example.com\",\"telefonoCliente\":\"0981000000\",\"ciCliente\":\"1112223\"}", habitacionId);

        Integer reservaId =
            given()
                .contentType("application/json")
                .body(body)
            .when()
                .post("/reservas")
            .then()
                .statusCode(201)
                .body("estado", is("PENDIENTE"))
                .extract().path("id");

        // Obtener por ID
        given()
        .when()
            .get("/reservas/" + reservaId)
        .then()
            .statusCode(200)
            .body("id", is(reservaId));
    }
}

