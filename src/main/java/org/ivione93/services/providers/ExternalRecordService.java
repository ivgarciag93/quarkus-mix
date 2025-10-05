package org.ivione93.services.providers;

import jakarta.enterprise.context.ApplicationScoped;
import org.ivione93.dto.record.ExternalDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class ExternalRecordService {

  public ExternalDto getExternalResponse() {
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
}