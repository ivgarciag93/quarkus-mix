package org.ivione93.dto.record;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RegisterForReflection
public record ExternalDto(
  BigDecimal initBalanceAmount,
  BigDecimal endBalanceAmount,
  List<ExternalMovement> movements
) {
  public record ExternalMovement(
    LocalDate creationDate,
    BigDecimal amount,
    String name
  ) {}
}
