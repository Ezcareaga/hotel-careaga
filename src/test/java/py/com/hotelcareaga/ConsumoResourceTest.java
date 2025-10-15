package py.com.hotelcareaga;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class ConsumoResourceTest {

    @Test
    void crearYObtenerConsumo() {
        // 1) Crear habitación
        Integer habitacionId =
            given()
                .contentType("application/json")
                .body("{\"numero\":\"T301\",\"tipo\":\"SIMPLE\",\"precio\":120000,\"capacidad\":2}")
            .when()
                .post("/habitaciones")
            .then()
                .statusCode(201)
                .extract().path("id");

        // 2) Crear reserva
        String reservaBody = String.format("{\"idHabitacion\":%d,\"fechaInicio\":\"2025-12-01\",\"fechaFin\":\"2025-12-03\",\"nombreCliente\":\"Luis Gomez\",\"emailCliente\":\"luis@example.com\",\"telefonoCliente\":\"0981999999\",\"ciCliente\":\"2223334\"}", habitacionId);
        Integer reservaId =
            given()
                .contentType("application/json")
                .body(reservaBody)
            .when()
                .post("/reservas")
            .then()
                .statusCode(201)
                .extract().path("id");

        // 3) Crear servicio extra
        Integer servicioId =
            given()
                .contentType("application/json")
                .body("{\"nombre\":\"Lavanderia\",\"precio\":20000,\"descripcion\":\"Prenda\"}")
            .when()
                .post("/servicios-extra")
            .then()
                .statusCode(201)
                .extract().path("id");

        // 4) Crear consumo
        String consumoBody = String.format("{\"reservaId\":%d,\"servicioId\":%d,\"cantidad\":2}", reservaId, servicioId);
        Integer consumoId =
            given()
                .contentType("application/json")
                .body(consumoBody)
            .when()
                .post("/consumos")
            .then()
                .statusCode(201)
                .body("cantidad", is(2))
                .extract().path("id");

        // 5) Obtener por ID
        given()
        .when()
            .get("/consumos/" + consumoId)
        .then()
            .statusCode(200)
            .body("id", is(consumoId));
    }
}
