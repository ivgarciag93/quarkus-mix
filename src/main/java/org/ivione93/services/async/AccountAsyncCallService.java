package org.ivione93.services.async;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.ivione93.dto.accountdata.AccountBalanceResponse;
import org.ivione93.dto.accountdata.AccountStock;
import org.ivione93.services.providers.FranchisesAccountService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class AccountAsyncCallService extends AsyncCallService {

    @Inject
    FranchisesAccountService franchisesAccountService;


    public CompletableFuture<AccountBalanceResponse> getOutstandingBalance(final int storeCode, final String fiscalId) {
        return managedExecutor
            .supplyAsync(() -> franchisesAccountService.getOutstandingBalance(storeCode, fiscalId))
            .orTimeout(timeoutMilliseconds, TimeUnit.MILLISECONDS)
            .exceptionally(ex -> {
                Log.errorf(ex, "Error obtaining outstanding balance in account-data");
                throw toCompletionException(ex);
            });
    }

    public CompletableFuture<AccountBalanceResponse> getDailyMovements(final int storeCode, final String fiscalId) {
        return managedExecutor
            .supplyAsync(() -> franchisesAccountService.getDailyMovements(storeCode, fiscalId))
            .orTimeout(timeoutMilliseconds, TimeUnit.MILLISECONDS)
            .exceptionally(ex -> {
                Log.errorf(ex, "Error obtaining daily movements in account-data");
                throw toCompletionException(ex);
            });
    }

    public CompletableFuture<AccountStock> getStock() {
        return managedExecutor
            .supplyAsync(() -> franchisesAccountService.getStock())
            .orTimeout(timeoutMilliseconds, TimeUnit.MILLISECONDS)
            .exceptionally(ex -> {
                Log.errorf(ex, "Error obtaining stock in account-data");
                throw toCompletionException(ex);
            });
    }

}
