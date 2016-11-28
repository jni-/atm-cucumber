package ca.ulaval.glo4002.features.fixtures.mediumapp;

import static org.junit.Assert.*;

import ca.ulaval.glo4002.atm_api.application.ServiceLocator;
import ca.ulaval.glo4002.atm_api.domain.accounts.Account;
import ca.ulaval.glo4002.atm_api.domain.accounts.AccountRepository;
import ca.ulaval.glo4002.atm_api.domain.accounts.StandardAccount;
import ca.ulaval.glo4002.features.fixtures.AccountFixture;

public class MediumAppAccoungFixture implements AccountFixture {

    private AccountRepository accountRepository;
    
    public MediumAppAccoungFixture() {
        accountRepository = ServiceLocator.resolve(AccountRepository.class);
    }

    @Override
    public void givenAnAccount(Integer accountNumber, Double initialAmount) {
        Account account = new StandardAccount(accountNumber, initialAmount);
        accountRepository.persist(account);
    }

    @Override
    public void thenAccountBalanceEquals(int accountNumber, double expectedBalance) {
        Account account = accountRepository.findByNumber(accountNumber);

        assertEquals(expectedBalance, account.getBalance(), 0.01);
    }

    @Override
    public void givenTheLimitForAccount(Integer accountNumber, Double maxLimit) {
//        Account account = accountRepository.findByNumber(accountNumber);
//        account.setMaxTransactionLimit(maxLimit);
    }

}
