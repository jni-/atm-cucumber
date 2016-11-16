package ca.ulaval.glo4002.atm_api.domain.accounts.transactions;

import java.util.UUID;

public class TransactionLog {

    public static TransactionLog accepted(double amount) {
        return new TransactionLog(true, amount);
    }

    public static TransactionLog refused(double amount) {
        return new TransactionLog(false, amount);
    }

    private UUID id;
    private boolean accepted;
    private double amount;

    private TransactionLog(boolean accepted, double amount) {
        this.id = UUID.randomUUID();
        this.accepted = accepted;
        this.amount = amount;
    }
    
    public UUID getId() {
        return id;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public double getAmount() {
        return amount;
    }

}
