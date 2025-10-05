package org.ivione93.services;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import org.ivione93.dto.account.BalanceResponse;
import org.ivione93.dto.accountdata.AccountBalanceResponse;
import org.ivione93.dto.accountdata.AccountStock;
import org.ivione93.services.converters.AccountConverterService;

import java.util.concurrent.CompletableFuture;

@ApplicationScoped
public class AccountService {

    @Inject
    AsynCallService asynCallService;

    @Inject
    AccountConverterService accountConverterService;

    public BalanceResponse getOutstandingBalance(final int storeCode, final String fiscalId) {
        CompletableFuture<AccountBalanceResponse> futureAccountBalance =
            asynCallService.getOutstandingBalance(storeCode, fiscalId);
        try {
            return accountConverterService.fillOutstandingBalance(futureAccountBalance.get());
        } catch (Exception ex) {
            Log.errorf(ex, "Error while obtaining outstanding balance");
            throw new WebApplicationException("Unable to obtain outstanding balance", ex);
        }
    }

    public BalanceResponse getDailyMovements(final int storeCode, final String fiscalId) {
        CompletableFuture<AccountBalanceResponse> futureAccountBalance =
            asynCallService.getDailyMovements(storeCode, fiscalId);
        try {
            return accountConverterService.fillDailyMovements(futureAccountBalance.get());
        } catch (Exception ex) {
            Log.errorf(ex, "Error while obtaining daily movements");
            throw new WebApplicationException("Unable to obtain daily movements", ex);
        }
    }

    public BalanceResponse getTotalDebt(final int storeCode, final String fiscalId) {
        CompletableFuture<AccountBalanceResponse> futureAccountBalance =
            asynCallService.getDailyMovements(storeCode, fiscalId);

        CompletableFuture<AccountStock> futureAccountStock = asynCallService.getStock();

        try {
            CompletableFuture.allOf(futureAccountBalance, futureAccountStock).join();
            return accountConverterService.fillTotalDebt(futureAccountBalance.get(), futureAccountStock.get());
        } catch (Exception ex) {
            Log.errorf(ex, "Error while obtaining total debt");
            throw new WebApplicationException("Unable to obtain total debt", ex);
        }
    }

}