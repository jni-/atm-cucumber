package ca.ulaval.glo4002.features.fixtures;

import static org.hamcrest.Matchers.equalTo;

import java.net.URI;

import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import ca.ulaval.glo4002.atm_api.rest.MoneyTransferResource;
import ca.ulaval.glo4002.atm_api.rest.TransferRequest;
import io.restassured.response.Response;

public class TransferMoneyRestFixture extends BaseRestFixture {
    
    private Response currentRequest;
    
    public void whenMoneyIsTransfered(int sourceAccountNumber, int recipientAccountNumber, double amount) {
        String path = MoneyTransferResource.TRANSFER_PATH + "/{recipient}";
        URI uri = UriBuilder.fromPath(path).build(sourceAccountNumber, recipientAccountNumber);

        currentRequest = givenAtmRequest()
            .body(new TransferRequest(amount))
        .when()
            .post(uri);
    }
    
    public void thenTheTransactionIsAccepted(double amount) {
        currentRequest.then()
            .statusCode(Status.CREATED.getStatusCode())
            .and().body("accepted", equalTo(true))
            .and().body("amount", equalTo((float) amount));
    }

    public void thenTheTransactionIsRefused() {
        currentRequest.then()
            .statusCode(Status.CREATED.getStatusCode())
            .and().body("accepted", equalTo(false));
    }
}
