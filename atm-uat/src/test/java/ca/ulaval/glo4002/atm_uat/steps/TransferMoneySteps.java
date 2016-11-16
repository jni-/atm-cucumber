package ca.ulaval.glo4002.atm_uat.steps;

import ca.ulaval.glo4002.atm_uat.fixtures.AccountFixture;
import ca.ulaval.glo4002.atm_uat.fixtures.TransferMoneyRestFixture;
import cucumber.api.java8.En;

public class TransferMoneySteps implements En {

    public TransferMoneySteps() {
        
        TransferMoneyRestFixture transferMoney = new TransferMoneyRestFixture();
        AccountFixture accounts = new AccountFixture();

        Given("^an account (\\d+) with (\\d+)\\$ in it$", (Integer accountNumber, Double initialAmount) -> {
            accounts.givenAnAccount(accountNumber, initialAmount);
        });

        When("^I transfer (\\d+)\\$ from (\\d+) to (\\d+)$", (Double amount, Integer sourceAccountNumber, Integer recipientAccountNumber) -> {
            transferMoney.whenMoneyIsTransfered(sourceAccountNumber, recipientAccountNumber, amount);
        });

        Then("^the account (\\d+) has (\\d+)\\$ in it$", (Integer accountNumber, Double expectedAmount) -> {
            accounts.thenAccountBalanceEquals(accountNumber, expectedAmount);
        });

    }

}
