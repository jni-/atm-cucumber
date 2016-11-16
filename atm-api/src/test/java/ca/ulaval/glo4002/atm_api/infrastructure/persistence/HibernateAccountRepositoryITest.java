package ca.ulaval.glo4002.atm_api.infrastructure.persistence;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import ca.ulaval.glo4002.atm_api.application.jpa.EntityManagerProvider;
import ca.ulaval.glo4002.atm_api.domain.accounts.Account;
import ca.ulaval.glo4002.atm_api.domain.accounts.StandardAccount;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class HibernateAccountRepositoryITest {

    private static final int ACCOUNT_NUMBER = 123;
    private static final double BALANCE = 1000.00;

    private static EntityManagerFactory entityManagerFactory;

    private HibernateAccountRepository accountRepository;

    @BeforeClass
    public static void setUpClass() {
        entityManagerFactory = Persistence.createEntityManagerFactory("atm-test");
    }

    @Before
    public void setUp() {
        EntityManagerProvider.setEntityManager(entityManagerFactory.createEntityManager());

        accountRepository = new HibernateAccountRepository();
    }

    @Test
    public void canSaveAndRetrieveAccount() {
        Account account = new StandardAccount(ACCOUNT_NUMBER, BALANCE);

        new EntityManagerProvider().executeInTransaction(() -> accountRepository.persist(account));

        Account retrievedAccount = accountRepository.findByNumber(ACCOUNT_NUMBER);
        assertSameAccount(account, retrievedAccount);
    }

    private void assertSameAccount(Account account, Account retrievedAccount) {
        assertEquals(account.getAccountNumber(), retrievedAccount.getAccountNumber());
    }

}
