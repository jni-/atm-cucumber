package ca.ulaval.glo4002.atm_api.application.banking;

import static org.junit.Assert.assertSame;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4002.atm_api.application.jpa.ThreadLocalJpaEntityManagerProvider;
import ca.ulaval.glo4002.atm_api.application.jpa.EntityManagerProviderForTests;
import ca.ulaval.glo4002.atm_api.domain.accounts.Account;
import ca.ulaval.glo4002.atm_api.domain.accounts.AccountRepository;
import ca.ulaval.glo4002.atm_api.domain.accounts.transactions.TransactionLog;
import ca.ulaval.glo4002.atm_api.rest.TransferRequest;
import org.junit.Before;
import org.junit.Test;

public class BankingServiceTest {

    private static final int FROM_ACCOUNT_NUMBER = 124;
    private static final int TO_ACCOUNT_NUMBER = 987;

    private TransferRequest transferRequest;
    private AccountRepository accountRepository;
    private Account fromAccount;
    private Account toAccount;

    private BankingService bankingService;


    @Before
    public void setUp() {
        transferRequest = new TransferRequest(80);
        fromAccount = mock(Account.class);
        toAccount = mock(Account.class);

        accountRepository = mock(AccountRepository.class);
        ThreadLocalJpaEntityManagerProvider entityManagerProvider = new EntityManagerProviderForTests();
        bankingService = new BankingService(accountRepository, entityManagerProvider);

        willReturn(fromAccount).given(accountRepository).findByNumber(FROM_ACCOUNT_NUMBER);
        willReturn(toAccount).given(accountRepository).findByNumber(TO_ACCOUNT_NUMBER);
    }
    
    @Test
    public void transferMoneyExecutesTheTransfer() {
        bankingService.transferMoney(FROM_ACCOUNT_NUMBER, TO_ACCOUNT_NUMBER, transferRequest);
        
        verify(fromAccount).transferMoneyTo(toAccount, transferRequest.amount);
    }
    
    @Test
    public void transferMoneyPersistsBothAccounts() {
        bankingService.transferMoney(FROM_ACCOUNT_NUMBER, TO_ACCOUNT_NUMBER, transferRequest);
        
        verify(accountRepository).persist(fromAccount);
        verify(accountRepository).persist(toAccount);
    }
    
    @Test
    public void transferMoneyReturnsTheGeneratedLog() {
        TransactionLog expectedLog = mock(TransactionLog.class);
        willReturn(expectedLog).given(fromAccount).transferMoneyTo(any(), anyDouble());
        
        TransactionLog log = bankingService.transferMoney(FROM_ACCOUNT_NUMBER, TO_ACCOUNT_NUMBER, transferRequest);
        
        assertSame(expectedLog, log);
    }
}
