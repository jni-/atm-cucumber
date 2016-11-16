package ca.ulaval.glo4002.atm_api.rest;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import ca.ulaval.glo4002.atm_api.application.ServiceLocator;
import ca.ulaval.glo4002.atm_api.application.banking.BankingService;
import ca.ulaval.glo4002.atm_api.domain.accounts.transactions.TransactionLog;

@Path(MoneyTransferResource.TRANSFER_PATH)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MoneyTransferResource {

    public static final String TRANSFER_PATH = "/accounts/{accountNumber}/transfer";

    private BankingService bankingService;

    public MoneyTransferResource() {
        bankingService = ServiceLocator.resolve(BankingService.class);
    }

    public MoneyTransferResource(BankingService bankingService) {
        this.bankingService = bankingService;
    }

    @POST
    @Path("/{recipient}")
    public Response transferMoney(
            @PathParam("accountNumber") int accountNumber,
            @PathParam("recipient") int recipientAccountNumber,
            TransferRequest transferRequest) {
        TransactionLog log = bankingService.transferMoney(accountNumber, recipientAccountNumber, transferRequest);

        URI location = UriBuilder.fromPath(TRANSFER_PATH + "/{transactionId}").build(accountNumber, log.getId());
        return Response.created(location).entity(log).build();
    }

}
