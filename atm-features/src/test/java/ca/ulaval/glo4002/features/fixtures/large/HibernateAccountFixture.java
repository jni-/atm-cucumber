package ca.ulaval.glo4002.features.fixtures.large;

import static org.junit.Assert.assertEquals;

import ca.ulaval.glo4002.atm_api.domain.accounts.Account;
import ca.ulaval.glo4002.atm_api.domain.accounts.StandardAccount;
import ca.ulaval.glo4002.atm_api.infrastructure.persistence.HibernateAccountRepository;
import ca.ulaval.glo4002.features.fixtures.AccountFixture;

public class HibernateAccountFixture extends HibernateBaseFixture implements AccountFixture {

    private static final double DELTA = 0.001;

    private HibernateAccountRepository accountRepository;

    public HibernateAccountFixture() {
        accountRepository = new HibernateAccountRepository();
    }

    @Override
    public void givenAnAccount(Integer accountNumber, Double initialAmount) {
        withEntityManager((tx) -> {
            tx.begin();
            accountRepository.persist(new StandardAccount(accountNumber, initialAmount));
            tx.commit();
        });
    }

    @Override
    public void thenAccountBalanceEquals(int accountNumber, double expectedBalance) {
        Account account = withEntityManager((tx) -> {
            return accountRepository.findByNumber(accountNumber);
        });

        assertEquals(expectedBalance, account.getBalance(), DELTA);
    }

    @Override
    public void givenTheLimitForAccount(Integer accountNumber, Double maxLimit) {
        throw new RuntimeException("TODO");
    }

}
