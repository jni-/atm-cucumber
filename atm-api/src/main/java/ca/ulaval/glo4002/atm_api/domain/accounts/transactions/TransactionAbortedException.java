package ca.ulaval.glo4002.atm_api.domain.accounts.transactions;

public class TransactionAbortedException extends RuntimeException {

    public TransactionAbortedException() {
    }

    public TransactionAbortedException(Throwable cause) {
        super(cause);
    }

    private static final long serialVersionUID = 1L;

}
