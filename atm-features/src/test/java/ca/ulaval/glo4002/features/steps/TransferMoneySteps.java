package ca.ulaval.glo4002.features.steps;

import ca.ulaval.glo4002.features.fixtures.AccountFixture;
import ca.ulaval.glo4002.features.fixtures.TransferMoneyFixture;
import ca.ulaval.glo4002.features.fixtures.domain.AcceptanceDomainContext;
import ca.ulaval.glo4002.features.fixtures.domain.DomainAccoungFixture;
import ca.ulaval.glo4002.features.fixtures.domain.DomainTransferMoneyFixture;
import ca.ulaval.glo4002.features.fixtures.large.AcceptanceLargeContext;
import ca.ulaval.glo4002.features.fixtures.large.HibernateAccountFixture;
import ca.ulaval.glo4002.features.fixtures.large.RestTransferMoneyFixture;
import ca.ulaval.glo4002.features.runners.JettyStarterHook;
import cucumber.api.java.Before;
import cucumber.api.java8.En;

public class TransferMoneySteps implements En {

    private TransferMoneyFixture transferMoney;
    private AccountFixture accounts;

    @Before
    public void beforeScenario() throws Throwable {

        String testsScope = System.getProperty("acctests.scope", "UNDEFINED");

        switch (testsScope) {
            case "large":
                new AcceptanceLargeContext().apply();
                
                new JettyStarterHook().runStartServerHook();
                
                transferMoney = new RestTransferMoneyFixture();
                accounts = new HibernateAccountFixture();
                break;
                
            case "domain":
                new AcceptanceDomainContext().apply();
                transferMoney = new DomainTransferMoneyFixture();
                accounts = new DomainAccoungFixture();
                break;

            default:
                throw new UnsupportedOperationException(String.format("Acceptance test scope '%s' is not supported", testsScope));
        }

    }

    public TransferMoneySteps() {

        Given("^an account (\\d+) with (\\d+)\\$ in it$", (Integer accountNumber, Double initialAmount) -> {
            accounts.givenAnAccount(accountNumber, initialAmount);
        });

        When("^I create a transaction of (\\d+)\\$ from (\\d+) to (\\d+)$", (Double amount, Integer sourceAccountNumber, Integer recipientAccountNumber) -> {
            transferMoney.whenMoneyIsTransfered(sourceAccountNumber, recipientAccountNumber, amount);
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
