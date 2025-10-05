package org.ivione93.dto.accountdata;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.math.BigDecimal;
import java.time.LocalDate;

@RegisterForReflection
public class AccountMovement {

    public LocalDate movementDate;
    public String movementName;
    public String movementCode;
    public String movementType;
    public BigDecimal amount;

}
