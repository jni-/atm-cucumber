package ca.ulaval.glo4002.features.steps;

import ca.ulaval.glo4002.features.contexts.AcceptanceContext;
import ca.ulaval.glo4002.features.fixtures.AccountFixture;
import ca.ulaval.glo4002.features.fixtures.BankFixture;
import ca.ulaval.glo4002.features.fixtures.TransferMoneyRestFixture;
import cucumber.api.java.Before;
import cucumber.api.java8.En;

public class TransferMoneySteps implements En {
    
    private TransferMoneyRestFixture transferMoney;
    private AccountFixture accounts;
    private BankFixture bank;

    @Before
    public void beforeScenario() {
        new AcceptanceContext().apply();

        transferMoney = new TransferMoneyRestFixture();
        accounts = new AccountFixture();
        bank = new BankFixture();
    }

    public TransferMoneySteps() {

        Given("^an account (\\d+) with (\\d+)\\$ in it$", (Integer accountNumber, Double initialAmount) -> {
            accounts.givenAnAccount(accountNumber, initialAmount);
        });
        
        When("^I create a transaction of (\\d+)\\$ from (\\d+) to (\\d+)$", (Double amount, Integer sourceAccountNumber, Integer recipientAccountNumber) -> {
            bank.whenATransferIsMade(sourceAccountNumber, recipientAccountNumber, amount);
        });

        When("^I transfer (\\d+)\\$ from (\\d+) to (\\d+)$", (Double amount, Integer sourceAccountNumber, Integer recipientAccountNumber) -> {
            transferMoney.whenMoneyIsTransfered(sourceAccountNumber, recipientAccountNumber, amount);
        });

        Then("^the account (\\d+) has (\\d+)\\$ in it$", (Integer accountNumber, Double expectedAmount) -> {
            accounts.thenAccountBalanceEquals(accountNumber, expectedAmount);
        });
        
        Then("^a transaction log is created for the amount of (\\d+)\\$$", (Double expectedAmount) -> {
            transferMoney.thenTheTransactionIsAccepted(expectedAmount);
        });
        
        Then("^a transaction log shows that the transfer was refused$", () -> {
            transferMoney.thenTheTransactionIsRefused();
        });

    }

}
