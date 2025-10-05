package org.ivione93.dto.record;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RegisterForReflection
public record InternalDto(
    BigDecimal initBalanceAmount,
    BigDecimal endBalanceAmount,
    List<InternalMovement> movements
) {
  public record InternalMovement(
    LocalDate creationDate,
    String name,
    BigDecimal amount,
    BigDecimal pendingAmount
  ) {}
}
