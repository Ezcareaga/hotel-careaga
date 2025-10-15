package py.com.hotelcareaga;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class HabitacionResourceTest {

    @Test
    void crearYObtenerHabitacion() {
        Integer id =
            given()
                .contentType("application/json")
                .body("{\"numero\":\"T101\",\"tipo\":\"SIMPLE\",\"precio\":150000,\"capacidad\":2}")
            .when()
                .post("/habitaciones")
            .then()
                .statusCode(201)
                .body("numero", is("T101"))
                .extract().path("id");

        given()
        .when()
            .get("/habitaciones/" + id)
        .then()
            .statusCode(200)
            .body("id", is(id));
    }
}
