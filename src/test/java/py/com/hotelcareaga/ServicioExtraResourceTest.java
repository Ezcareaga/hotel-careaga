package py.com.hotelcareaga;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class ServicioExtraResourceTest {

    @Test
    void crearYObtenerServicioExtra() {
        Integer id =
            given()
                .contentType("application/json")
                .body("{\"nombre\":\"Desayuno\",\"precio\":15000,\"descripcion\":\"Buffet\"}")
            .when()
                .post("/servicios-extra")
            .then()
                .statusCode(201)
                .body("nombre", is("Desayuno"))
                .extract().path("id");

        given()
        .when()
            .get("/servicios-extra/" + id)
        .then()
            .statusCode(200)
            .body("id", is(id));
    }
}
