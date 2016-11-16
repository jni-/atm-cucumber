package ca.ulaval.glo4002.atm_uat.fixtures;

import static org.junit.Assert.assertEquals;

import ca.ulaval.glo4002.atm_api.domain.accounts.Account;
import ca.ulaval.glo4002.atm_api.domain.accounts.StandardAccount;
import ca.ulaval.glo4002.atm_api.infrastructure.persistence.HibernateAccountRepository;

public class AccountFixture extends HibernateBaseFixture {

    private static final double DELTA = 0.001;

    private HibernateAccountRepository accountRepository;

    public AccountFixture() {
        accountRepository = new HibernateAccountRepository();
    }

    public void givenAnAccount(Integer accountNumber, Double initialAmount) {
        withEntityManager((tx) -> {
            tx.begin();
            accountRepository.persist(new StandardAccount(accountNumber, initialAmount)); 
            tx.commit();
        });
    }

    public void thenAccountBalanceEquals(int accountNumber, double expectedBalance) {
        Account account = withEntityManager((tx) -> {
            return accountRepository.findByNumber(accountNumber);
        });

        assertEquals(expectedBalance, account.getBalance(), DELTA);
    }
}
