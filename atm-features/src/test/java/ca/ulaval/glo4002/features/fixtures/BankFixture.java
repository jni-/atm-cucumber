package ca.ulaval.glo4002.features.fixtures;

public interface BankFixture {

    void whenATransferIsMade(Integer sourceAccountNumber, Integer recipientAccountNumber, Double amount);

}