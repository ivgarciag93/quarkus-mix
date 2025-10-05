package org.ivione93.dto.account;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.math.BigDecimal;
import java.time.LocalDate;

@RegisterForReflection
public class StockBalanceResponse extends BalanceResponse {

    public BigDecimal stockTotalAmountWithVAT;
    public LocalDate stockLastUpdateDate;

}
