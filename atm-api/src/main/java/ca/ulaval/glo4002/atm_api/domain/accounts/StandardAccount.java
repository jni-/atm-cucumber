package ca.ulaval.glo4002.atm_api.domain.accounts;

import javax.persistence.Entity;

import ca.ulaval.glo4002.atm_api.domain.accounts.transactions.TransactionLog;

@Entity
public class StandardAccount extends Account {

    protected StandardAccount() {
        super(); // for hibernate
    }

    public StandardAccount(int accountNumber, double balance) {
        super(accountNumber, balance);
    }

    public TransactionLog transferMoneyTo(Account recipientAccount, double amount) {
        if(amount > maxPerTransactionLimit) {
            return TransactionLog.refused(amount);
        }
        
        if(amount > balance) {
            return TransactionLog.refused(amount);
        }
        
        balance -= amount;
        recipientAccount.credit(amount);
        
        return TransactionLog.accepted(amount);
    }

    public void credit(double amount) {
        balance += amount;
    }

    protected double getMaxPerTransactionLimit() {
        return maxPerTransactionLimit;
    }

}
