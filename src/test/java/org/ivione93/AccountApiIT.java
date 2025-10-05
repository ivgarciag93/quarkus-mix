package org.ivione93;

import io.quarkus.test.junit.QuarkusIntegrationTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;

@QuarkusIntegrationTest
public class AccountApiIT extends AccountApiTest {

    @Test
    void integrationTest() {
        given()
            .pathParam("storeCode", STORE_CODE)
            .pathParam("fiscalId", FISCAL_ID)
            .when()
            .get(GET_OUTSTANDING_BALANCE_URI)
            .then()
            .body("movements.movementType", hasItems("Tipo 1", "Tipo 2", "Tipo 3", "Tipo 4", "Tipo 5"))
            .body("initBalanceAmount", is(10.0f))
            .statusCode(200);
    }
}
