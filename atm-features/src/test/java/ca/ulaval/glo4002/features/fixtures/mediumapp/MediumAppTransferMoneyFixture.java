package ca.ulaval.glo4002.features.fixtures.mediumapp;

import static org.junit.Assert.*;

import ca.ulaval.glo4002.atm_api.application.ServiceLocator;
import ca.ulaval.glo4002.atm_api.application.banking.BankingService;
import ca.ulaval.glo4002.atm_api.domain.accounts.transactions.TransactionLog;
import ca.ulaval.glo4002.atm_api.rest.TransferRequest;
import ca.ulaval.glo4002.features.fixtures.TransferMoneyFixture;

public class MediumAppTransferMoneyFixture implements TransferMoneyFixture {

    private TransactionLog transferMoneyLog;

    @Override
    public void whenMoneyIsTransfered(int sourceAccountNumber, int recipientAccountNumber, double amount) {
        TransferRequest transferRequest = new TransferRequest(amount);
        
        BankingService bankingService = ServiceLocator.resolve(BankingService.class);
        transferMoneyLog = bankingService.transferMoney(sourceAccountNumber, recipientAccountNumber, transferRequest);
    }

    @Override
    public void thenTheTransactionIsAccepted(double amount) {
        assertTrue(transferMoneyLog.isAccepted());
    }

    @Override
    public void thenTheTransactionIsRefused() {
        assertFalse(transferMoneyLog.isAccepted());
    }

}
