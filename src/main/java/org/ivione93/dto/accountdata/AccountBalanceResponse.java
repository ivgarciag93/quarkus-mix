package org.ivione93.dto.accountdata;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.math.BigDecimal;
import java.util.List;

@RegisterForReflection
public class AccountBalanceResponse {

    public BigDecimal initBalanceAmount;
    public BigDecimal endBalanceAmount;
    public List<AccountMovement> movements;

}
