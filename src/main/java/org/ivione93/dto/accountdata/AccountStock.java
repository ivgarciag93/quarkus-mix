package org.ivione93.dto.accountdata;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.math.BigDecimal;
import java.time.LocalDate;

@RegisterForReflection
public class AccountStock {

    public BigDecimal stockTotalAmountWithVAT;
    public LocalDate stockLastUpdateDate;

}
