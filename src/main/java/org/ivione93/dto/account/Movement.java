package org.ivione93.dto.account;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.math.BigDecimal;
import java.time.LocalDate;

@RegisterForReflection
public class Movement {

    public LocalDate movementDate;
    public String movementName;
    public String movementCode;
    public String movementType;
    public BigDecimal amount;
    public BigDecimal pendingAmount;

}
