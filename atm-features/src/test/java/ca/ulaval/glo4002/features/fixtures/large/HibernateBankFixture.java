package ca.ulaval.glo4002.features.fixtures.large;

import ca.ulaval.glo4002.atm_api.application.banking.BankingService;
import ca.ulaval.glo4002.atm_api.rest.TransferRequest;
import ca.ulaval.glo4002.features.fixtures.BankFixture;

public class HibernateBankFixture extends HibernateBaseFixture implements BankFixture {
    
    private BankingService bank;

    public HibernateBankFixture() {
        bank = new BankingService();
    }

    /* (non-Javadoc)
     * @see ca.ulaval.glo4002.features.fixtures.large.BankFixture#whenATransferIsMade(java.lang.Integer, java.lang.Integer, java.lang.Double)
     */
    @Override
    public void whenATransferIsMade(Integer sourceAccountNumber, Integer recipientAccountNumber, Double amount) {
        TransferRequest transferRequest = new TransferRequest(amount);

        withEntityManager((tx) -> {
            bank.transferMoney(sourceAccountNumber, recipientAccountNumber, transferRequest);
        });
    }
}
