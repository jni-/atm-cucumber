package ca.ulaval.glo4002.features.fixtures.large;

import static org.hamcrest.Matchers.equalTo;

import java.net.URI;

import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import ca.ulaval.glo4002.atm_api.rest.MoneyTransferResource;
import ca.ulaval.glo4002.atm_api.rest.TransferRequest;
import ca.ulaval.glo4002.features.fixtures.TransferMoneyFixture;
import io.restassured.response.Response;

public class RestTransferMoneyFixture extends BaseRestFixture implements TransferMoneyFixture {
    
    private Response currentRequest;
    
    /* (non-Javadoc)
     * @see ca.ulaval.glo4002.features.fixtures.large.TransferMoneyFixture#whenMoneyIsTransfered(int, int, double)
     */
    @Override
    public void whenMoneyIsTransfered(int sourceAccountNumber, int recipientAccountNumber, double amount) {
        String path = MoneyTransferResource.TRANSFER_PATH + "/{recipient}";
        URI uri = UriBuilder.fromPath(path).build(sourceAccountNumber, recipientAccountNumber);

        currentRequest = givenAtmRequest()
            .body(new TransferRequest(amount))
        .when()
            .post(uri);
    }
    
    /* (non-Javadoc)
     * @see ca.ulaval.glo4002.features.fixtures.large.TransferMoneyFixture#thenTheTransactionIsAccepted(double)
     */
    @Override
    public void thenTheTransactionIsAccepted(double amount) {
        currentRequest.then()
            .statusCode(Status.CREATED.getStatusCode())
            .and().body("accepted", equalTo(true))
            .and().body("amount", equalTo((float) amount));
    }

    /* (non-Javadoc)
     * @see ca.ulaval.glo4002.features.fixtures.large.TransferMoneyFixture#thenTheTransactionIsRefused()
     */
    @Override
    public void thenTheTransactionIsRefused() {
        currentRequest.then()
            .statusCode(Status.CREATED.getStatusCode())
            .and().body("accepted", equalTo(false));
    }
}
