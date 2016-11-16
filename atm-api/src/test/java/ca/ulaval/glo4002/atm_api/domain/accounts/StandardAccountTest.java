package ca.ulaval.glo4002.atm_api.domain.accounts;

import static org.junit.Assert.*;

import ca.ulaval.glo4002.atm_api.domain.accounts.transactions.TransactionLog;
import org.junit.Before;
import org.junit.Test;

public class StandardAccountTest {
    
    private static final double DELTA = 0.0001;
    
    private static final double INITIAL_AMOUNT_SOURCE = 1000;
    private static final double INITIAL_AMOUNT_RECIPIENT = 500;
    private static final double TRANSFER_AMOUNT = 100;
    private static final int RECIPIENT_ACCOUNT_NUMBER = 2;

    private StandardAccount sourceAccount;
    private StandardAccount recipientAccount;

    @Before
    public void setUp() {
        sourceAccount = new StandardAccount(1, INITIAL_AMOUNT_SOURCE);
        recipientAccount = new StandardAccount(RECIPIENT_ACCOUNT_NUMBER, INITIAL_AMOUNT_RECIPIENT);
    }

    @Test
    public void transferMoneyReducesTheBalanceOfTheSourceAmount() {
        sourceAccount.transferMoneyTo(recipientAccount, TRANSFER_AMOUNT);
        
        assertEquals(INITIAL_AMOUNT_SOURCE - TRANSFER_AMOUNT, sourceAccount.balance, DELTA);
    }
    
    @Test
    public void transferMoneyIncreasesTheBalanceOfTheRecipient() {
        sourceAccount.transferMoneyTo(recipientAccount, TRANSFER_AMOUNT);
        
        assertEquals(INITIAL_AMOUNT_RECIPIENT + TRANSFER_AMOUNT, recipientAccount.balance, DELTA);
    }
    
    @Test
    public void transferMoneyRefusesTheTransferIfTheBalanceIsInsuffisant() {
        TransactionLog log = sourceAccount.transferMoneyTo(recipientAccount, INITIAL_AMOUNT_SOURCE + 1);
        
        assertFalse(log.isAccepted());
        assertEquals(INITIAL_AMOUNT_SOURCE + 1, log.getAmount(), DELTA);
    }
    
    @Test
    public void transferMoneyAcceptsTheTransferIfTheBalanceIsSuffisant() {
        TransactionLog log = sourceAccount.transferMoneyTo(recipientAccount, TRANSFER_AMOUNT);
        
        assertTrue(log.isAccepted());
        assertEquals(TRANSFER_AMOUNT, log.getAmount(), DELTA);
    }

}
