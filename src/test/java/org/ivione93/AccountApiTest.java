package org.ivione93;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.ivione93.dto.account.BalanceResponse;
import org.ivione93.dto.accountdata.AccountBalanceResponse;
import org.ivione93.services.providers.FranchisesAccountService;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class AccountApiTest {

    protected static final String BASE_URI = "/v1/account";
    protected static final String GET_OUTSTANDING_BALANCE_URI =
        BASE_URI + "/stores/{storeCode}/franchisee/{fiscalId}/outstanding-balance";
    protected static final String GET_DAILY_MOVEMENTS_URI =
        BASE_URI + "/stores/{storeCode}/franchisee/{fiscalId}/daily-movements";

    protected static final int STORE_CODE = 16161;
    protected static final String FISCAL_ID = "A12345678";

    @Inject
    FranchisesAccountService franchisesAccountService;

    @Test
    void testGetOutstandingBalance() {
        given()
            .pathParam("storeCode", STORE_CODE)
            .pathParam("fiscalId", FISCAL_ID)
            .when()
            .get(GET_OUTSTANDING_BALANCE_URI)
            .then()
            .log()
            .body()
            .body("movements.movementType", hasItems("Tipo 1", "Tipo 2", "Tipo 3", "Tipo 4", "Tipo 5"))
            .body("initBalanceAmount", is(10.0f))
            .statusCode(200);
    }

    @Test
    void testGetOutstandingBalanceBody() {
        AccountBalanceResponse expectedResponse =
            franchisesAccountService.getOutstandingBalance(1, "A1");

        BalanceResponse response =
            given()
                .pathParam("storeCode", STORE_CODE)
                .pathParam("fiscalId", FISCAL_ID)
                .when()
                .get(GET_OUTSTANDING_BALANCE_URI)
                .then()
                .extract()
                .body()
                .as(BalanceResponse.class);

        assertEquals(expectedResponse.initBalanceAmount, response.initBalanceAmount);
        assertEquals(expectedResponse.endBalanceAmount, response.endBalanceAmount);
        assertEquals(expectedResponse.movements.size(), response.movements.size());
    }

    @Test
    void testGetDailyMovements() {
        given()
            .pathParam("storeCode", STORE_CODE)
            .pathParam("fiscalId", FISCAL_ID)
            .when()
            .get(GET_DAILY_MOVEMENTS_URI)
            .then()
            .log()
            .body()
            .body("movements.movementType", hasItems("Tipo 1", "Tipo 2", "Tipo 3"))
            .body("initBalanceAmount", is(10.0f))
            .statusCode(200);
    }
}
