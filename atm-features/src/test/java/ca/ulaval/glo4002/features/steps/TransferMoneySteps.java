package ca.ulaval.glo4002.features.steps;

import ca.ulaval.glo4002.features.fixtures.AccountFixture;
import ca.ulaval.glo4002.features.fixtures.TransferMoneyFixture;
import ca.ulaval.glo4002.features.fixtures.large.AcceptanceLargeContext;
import ca.ulaval.glo4002.features.fixtures.large.HibernateAccountFixture;
import ca.ulaval.glo4002.features.fixtures.large.RestTransferMoneyFixture;
import ca.ulaval.glo4002.features.fixtures.mediumapp.AcceptanceMediumAppContext;
import ca.ulaval.glo4002.features.fixtures.mediumapp.MediumAppAccoungFixture;
import ca.ulaval.glo4002.features.fixtures.mediumapp.MediumAppTransferMoneyFixture;
import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java8.En;

public class TransferMoneySteps implements En {

    private static final int SOME_ACCOUNT_NUMBER = 1056;

    private TransferMoneyFixture transferMoneyFixture;
    private AccountFixture accountsFixture;

    //
    // *** Should be in a common hook or somewhere else on a real project
    //     because this hook is global to all Steps Definitions 
    //     but in this demo we have only one Step Definition so...
    //
    @Before
    public void beforeScenario() throws Throwable {
        
        String testsScope = System.getProperty("acctests.scope", "UNDEFINED");

        switch (testsScope) {
            case "large":
                new AcceptanceLargeContext().apply();
                
                transferMoneyFixture = new RestTransferMoneyFixture();
                accountsFixture = new HibernateAccountFixture();
                break;
                
            case "mediumapp":
                new AcceptanceMediumAppContext().apply();
                
                transferMoneyFixture = new MediumAppTransferMoneyFixture();
                accountsFixture = new MediumAppAccoungFixture();
                break;

            default:
                throw new UnsupportedOperationException(String.format("Acceptance test scope '%s' is not supported", testsScope));
        }

    }

    public TransferMoneySteps() {

        Given("^an account (\\d+) with (\\d+)\\$ in it$", (Integer accountNumber, Double initialAmount) -> {
            accountsFixture.givenAnAccount(accountNumber, initialAmount);
        });
        
        Given("^a maximum limit of (\\d+)\\$ per transfer for the account (\\d+)$", (Double maxLimit, Integer accountNumber) -> {
            accountsFixture.givenAnAccount(accountNumber, maxLimit + 1000);
            accountsFixture.givenTheLimitForAccount(accountNumber, maxLimit);
        });

        When("^I create a transaction of (\\d+)\\$ from (\\d+) to (\\d+)$", (Double amount, Integer sourceAccountNumber, Integer recipientAccountNumber) -> {
            transferMoneyFixture.whenMoneyIsTransfered(sourceAccountNumber, recipientAccountNumber, amount);
        });

        When("^I transfer (\\d+)\\$ from (\\d+) to (\\d+)$", (Double amount, Integer sourceAccountNumber, Integer recipientAccountNumber) -> {
            transferMoneyFixture.whenMoneyIsTransfered(sourceAccountNumber, recipientAccountNumber, amount);
        });

        When("^I transfer (\\d+)\\$ from (\\d+)$", (Double amount, Integer sourceAccountNumber) -> {
            int recipientAccountNumber = SOME_ACCOUNT_NUMBER;
            accountsFixture.givenAnAccount(recipientAccountNumber, 0.d);
            transferMoneyFixture.whenMoneyIsTransfered(sourceAccountNumber, recipientAccountNumber, amount);
        });

        Then("^the account (\\d+) has (\\d+)\\$ in it$", (Integer accountNumber, Double expectedAmount) -> {
            accountsFixture.thenAccountBalanceEquals(accountNumber, expectedAmount);
        });

        Then("^a transaction log is created for the amount of (\\d+)\\$$", (Double expectedAmount) -> {
            transferMoneyFixture.thenTheTransactionIsAccepted(expectedAmount);
        });

        Then("^a transaction log shows that the transfer was refused$", () -> {
            transferMoneyFixture.thenTheTransactionIsRefused();
        });

        
    }

}
