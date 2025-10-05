package org.ivione93.services.converters;

import jakarta.enterprise.context.ApplicationScoped;
import org.ivione93.dto.account.BalanceResponse;
import org.ivione93.dto.account.Movement;
import org.ivione93.dto.account.StockBalanceResponse;
import org.ivione93.dto.accountdata.AccountBalanceResponse;
import org.ivione93.dto.accountdata.AccountMovement;
import org.ivione93.dto.accountdata.AccountStock;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@ApplicationScoped
public class AccountConverterService {

    public BalanceResponse fillOutstandingBalance(AccountBalanceResponse accountBalanceResponse) {
        return fillBalance(accountBalanceResponse, this::toOutstandingBalanceMovement);
    }

    public BalanceResponse fillDailyMovements(AccountBalanceResponse accountBalanceResponse) {
        return fillBalance(accountBalanceResponse, this::toDailyMovement);
    }

    public StockBalanceResponse fillTotalDebt(
            AccountBalanceResponse accountBalanceResponse, AccountStock accountStockResponse) {
        StockBalanceResponse stockBalanceResponse = new StockBalanceResponse();
        BalanceResponse balanceResponse = fillBalance(accountBalanceResponse, this::toDailyMovement);
        stockBalanceResponse.stockTotalAmountWithVAT = accountStockResponse.stockTotalAmountWithVAT;
        stockBalanceResponse.stockLastUpdateDate = accountStockResponse.stockLastUpdateDate;
        stockBalanceResponse.initBalanceAmount = accountBalanceResponse.initBalanceAmount;
        stockBalanceResponse.endBalanceAmount = accountBalanceResponse.endBalanceAmount;
        stockBalanceResponse.movements = balanceResponse.movements;

        return stockBalanceResponse;
    }

    private BalanceResponse fillBalance(
            AccountBalanceResponse accountBalanceResponse,
            BiFunction<AccountMovement, BigDecimal, Movement> movementMapper) {

        AtomicReference<BigDecimal> pendingAmount = new AtomicReference<>(BigDecimal.ZERO);

        List<Movement> movementList = accountBalanceResponse.movements.stream()
            .map(movement -> {
                BigDecimal updated = pendingAmount.updateAndGet(val -> val.add(movement.amount));
                return movementMapper.apply(movement, updated);
            })
            .collect(Collectors.toList());

        BalanceResponse response = new BalanceResponse();
        response.initBalanceAmount = accountBalanceResponse.initBalanceAmount;
        response.endBalanceAmount = accountBalanceResponse.endBalanceAmount;
        response.movements = movementList;

        return response;
    }

    private Movement toOutstandingBalanceMovement(AccountMovement accountMovement, BigDecimal pendingAmount) {
        Movement m = new Movement();
        m.movementCode = accountMovement.movementCode;
        m.movementDate = accountMovement.movementDate;
        m.movementName = getMovementName(accountMovement.movementType, accountMovement.movementCode);
        m.movementType = accountMovement.movementType;
        m.amount = accountMovement.amount;
        m.pendingAmount = pendingAmount;
        return m;
    }

    private Movement toDailyMovement(AccountMovement accountMovement, BigDecimal pendingAmount) {
        Movement m = new Movement();
        m.movementCode = accountMovement.movementCode;
        m.movementDate = accountMovement.movementDate;
        m.movementName = accountMovement.movementType + " " + accountMovement.movementCode;
        m.movementType = accountMovement.movementType;
        m.amount = accountMovement.amount;
        m.pendingAmount = pendingAmount;
        return m;
    }

    private String getMovementName(final String movementType, final String movementCode) {
        return switch (movementType) {
            case "Tipo 1" -> "1" + movementType + " - " + movementCode;
            case "Tipo 2" -> "2" + movementType + " - " + movementCode;
            case "Tipo 3" -> "3" + movementType + " - " + movementCode;
            default -> movementType + " - " + movementCode;
        };
    }

}
