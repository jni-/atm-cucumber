package ca.ulaval.glo4002.atm_uat.fixtures;

import ca.ulaval.glo4002.atm_api.application.banking.BankingService;
import ca.ulaval.glo4002.atm_api.rest.TransferRequest;

public class BankFixture extends HibernateBaseFixture {
    
    private BankingService bank;

    public BankFixture() {
        bank = new BankingService();
    }

    public void whenATransferIsMade(Integer sourceAccountNumber, Integer recipientAccountNumber, Double amount) {
        TransferRequest transferRequest = new TransferRequest(amount);

        withEntityManager((tx) -> {
            bank.transferMoney(sourceAccountNumber, recipientAccountNumber, transferRequest);
        });
    }
}
