package org.ivione93;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import org.ivione93.dto.record.ExternalDto;
import org.ivione93.dto.record.InternalDto;
import org.ivione93.services.providers.ExternalRecordService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@QuarkusTest
public class RecordApiTest {

  private static final String GET_RECORDS_URI = "/v1/records";

  @InjectMock
  ExternalRecordService externalRecordService;

  @Test
  void cuando_pido_info_record_entonces_obtengo_respuesta_completa() {
    InternalDto expectedResponse = createInternalResponse();

    when(externalRecordService.getExternalResponse()).thenReturn(createExternalData());

    InternalDto result =
      given()
        .when()
        .get(GET_RECORDS_URI)
        .then()
        .statusCode(200)
        .extract()
        .body()
        .as(InternalDto.class);

    assertThat(result).usingRecursiveComparison().isEqualTo(expectedResponse);
  }

  private ExternalDto createExternalData() {
    ExternalDto.ExternalMovement movement1 = new ExternalDto.ExternalMovement(
      LocalDate.of(2025, 8, 1),
      BigDecimal.valueOf(-3.00),
      "Movement 1"
    );

    ExternalDto.ExternalMovement movement2 = new ExternalDto.ExternalMovement(
      LocalDate.of(2025, 8, 1),
      BigDecimal.valueOf(-1.00),
      "Movement 2"
    );

    ExternalDto.ExternalMovement movement3 = new ExternalDto.ExternalMovement(
      LocalDate.of(2025, 8, 2),
      BigDecimal.valueOf(-2.00),
      "Movement 3"
    );
    ExternalDto.ExternalMovement movement4 = new ExternalDto.ExternalMovement(
      LocalDate.of(2025, 8, 5),
      BigDecimal.valueOf(-4.00),
      "Movement 4"
    );

    List<ExternalDto.ExternalMovement> movements = List.of(movement1, movement2, movement3, movement4);
    return new ExternalDto(BigDecimal.TEN, BigDecimal.ZERO, movements);
  }

  private InternalDto createInternalResponse() {
    InternalDto.InternalMovement movement1 = new InternalDto.InternalMovement(
      LocalDate.of(2025, 8, 5),
      "Movement 4",
      BigDecimal.valueOf(-4.00),
      BigDecimal.valueOf(0.00)
    );

    InternalDto.InternalMovement movement2 = new InternalDto.InternalMovement(
      LocalDate.of(2025, 8, 2),
      "Movement 3",
      BigDecimal.valueOf(-2.00),
      BigDecimal.valueOf(4.00)
    );

    InternalDto.InternalMovement movement3 = new InternalDto.InternalMovement(
      LocalDate.of(2025, 8, 1),
      "Movement 2",
      BigDecimal.valueOf(-1.00),
      BigDecimal.valueOf(6.00)
    );

    InternalDto.InternalMovement movement4 = new InternalDto.InternalMovement(
      LocalDate.of(2025, 8, 1),
      "Movement 1",
      BigDecimal.valueOf(-3.00),
      BigDecimal.valueOf(7.00)
    );


    List<InternalDto.InternalMovement> movements = List.of(movement1, movement2, movement3, movement4);
    return new InternalDto(BigDecimal.TEN, BigDecimal.ZERO, movements);
  }
}
