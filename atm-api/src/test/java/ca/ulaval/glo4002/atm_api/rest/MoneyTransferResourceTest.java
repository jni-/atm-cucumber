package ca.ulaval.glo4002.atm_api.rest;

import static org.junit.Assert.assertSame;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;

import java.util.UUID;

import javax.ws.rs.core.Response;

import ca.ulaval.glo4002.atm_api.application.banking.BankingService;
import ca.ulaval.glo4002.atm_api.domain.accounts.transactions.TransactionLog;
import org.junit.Before;
import org.junit.Test;

public class MoneyTransferResourceTest {

    private static final int FROM_ACCOUNT_NUMBER = 123;
    private static final int TO_ACCOUNT_NUMBER = 987;

    private UUID transactionId;
    private MoneyTransferResource withdrawResource;
    private BankingService bankingService;
    private TransferRequest transferRequest;

    @Before
    public void setUp() {
        transactionId = UUID.randomUUID();
        bankingService = mock(BankingService.class);
        transferRequest = new TransferRequest(100);
        withdrawResource = new MoneyTransferResource(bankingService);
    }

    @Test
    public void transferMoneyReturnsTheResultingTransactionLog() {
        TransactionLog expectedTransactionLog = mock(TransactionLog.class);
        willReturn(expectedTransactionLog).given(bankingService).transferMoney(FROM_ACCOUNT_NUMBER, TO_ACCOUNT_NUMBER, transferRequest);
        willReturn(transactionId).given(expectedTransactionLog).getId();

        Response response = withdrawResource.transferMoney(FROM_ACCOUNT_NUMBER, TO_ACCOUNT_NUMBER, transferRequest);

        TransactionLog transactionLog = (TransactionLog) response.getEntity();
        assertSame(expectedTransactionLog, transactionLog);
    }

}
