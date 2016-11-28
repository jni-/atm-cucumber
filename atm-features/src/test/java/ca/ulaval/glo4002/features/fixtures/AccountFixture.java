package ca.ulaval.glo4002.features.fixtures;

public interface AccountFixture {

    void givenAnAccount(Integer accountNumber, Double initialAmount);

    void thenAccountBalanceEquals(int accountNumber, double expectedBalance);

}