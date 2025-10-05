package org.ivione93.dto.account;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.math.BigDecimal;
import java.util.List;

@RegisterForReflection
public class BalanceResponse {

    public BigDecimal initBalanceAmount;
    public BigDecimal endBalanceAmount;
    public List<Movement> movements;

}
