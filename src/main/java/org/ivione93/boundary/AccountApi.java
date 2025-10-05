package org.ivione93.boundary;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.ivione93.services.AccountService;

@ApplicationScoped
@Path("/v1/account")
public class AccountApi {

    @Inject
    AccountService accountService;

    @GET
    @Path("/stores/{storeCode}/franchisee/{fiscalId}/outstanding-balance")
    public Response getOutstandingBalance(@PathParam("storeCode") final Integer storeCode,
                                          @PathParam("fiscalId") final String fiscalId) {
        Log.infof("Call to getOutstandingBalance for storeCode %d and fiscalId %s", storeCode, fiscalId);
        return Response.ok().entity(accountService.getOutstandingBalance(storeCode, fiscalId)).build();
    }

    @GET
    @Path("/stores/{storeCode}/franchisee/{fiscalId}/daily-movements")
    public Response getDailyMovements(@PathParam("storeCode") final Integer storeCode,
                                      @PathParam("fiscalId") final String fiscalId) {
        Log.infof("Call to getDailyMovements for storeCode %d and fiscalId %s", storeCode, fiscalId);
        return Response.ok().entity(accountService.getDailyMovements(storeCode, fiscalId)).build();
    }

    @GET
    @Path("/stores/{storeCode}/franchisee/{fiscalId}/total-debt")
    public Response getTotalDebt(@PathParam("storeCode") final Integer storeCode,
                                      @PathParam("fiscalId") final String fiscalId) {
        Log.infof("Call to getTotalDebt for storeCode %d and fiscalId %s", storeCode, fiscalId);
        return Response.ok().entity(accountService.getTotalDebt(storeCode, fiscalId)).build();
    }

}
