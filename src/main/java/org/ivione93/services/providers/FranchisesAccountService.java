package org.ivione93.services.providers;

import jakarta.enterprise.context.ApplicationScoped;
import org.ivione93.dto.accountdata.AccountBalanceResponse;
import org.ivione93.dto.accountdata.AccountMovement;
import org.ivione93.dto.accountdata.AccountStock;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

@ApplicationScoped
public class FranchisesAccountService {

    public AccountBalanceResponse getOutstandingBalance(final int storeCode, final String fiscalId) {
        AccountBalanceResponse accountBalanceResponse = new AccountBalanceResponse();
        accountBalanceResponse.initBalanceAmount = new BigDecimal("10.0");
        accountBalanceResponse.endBalanceAmount = new BigDecimal("10.0");
        accountBalanceResponse.movements = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            AccountMovement accountMovement = new AccountMovement();
            accountMovement.movementType = "Tipo " + i;
            accountMovement.movementCode = "Code " + i;
            accountMovement.movementDate = LocalDate.parse("2025-08-18");
            accountMovement.movementName = String.valueOf(i);
            accountMovement.amount = new BigDecimal(i*10);
            accountBalanceResponse.movements.add(accountMovement);
        }

        return accountBalanceResponse;
    }

    public AccountBalanceResponse getDailyMovements(final int storeCode, final String fiscalId) {
        AccountBalanceResponse accountBalanceResponse = new AccountBalanceResponse();
        accountBalanceResponse.initBalanceAmount = new BigDecimal("10.0");
        accountBalanceResponse.endBalanceAmount = new BigDecimal("10.0");
        accountBalanceResponse.movements = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            AccountMovement accountMovement = new AccountMovement();
            accountMovement.movementType = "Tipo " + i;
            accountMovement.movementCode = "Code " + i;
            accountMovement.movementDate = LocalDate.parse("2025-08-18");
            accountMovement.movementName = String.valueOf(i);
            accountMovement.amount = new BigDecimal(i*10);
            accountBalanceResponse.movements.add(accountMovement);
        }

        return accountBalanceResponse;
    }

    public AccountStock getStock() {
        AccountStock accountStock = new AccountStock();
        accountStock.stockTotalAmountWithVAT = new BigDecimal("150.0");
        accountStock.stockLastUpdateDate = LocalDate.parse("2025-08-19");
        return accountStock;
    }
}
