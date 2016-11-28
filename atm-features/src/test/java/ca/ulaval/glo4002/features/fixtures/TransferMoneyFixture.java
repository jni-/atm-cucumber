package ca.ulaval.glo4002.features.fixtures;

public interface TransferMoneyFixture {

    void whenMoneyIsTransfered(int sourceAccountNumber, int recipientAccountNumber, double amount);

    void thenTheTransactionIsAccepted(double amount);

    void thenTheTransactionIsRefused();

}