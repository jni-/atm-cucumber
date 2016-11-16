package ca.ulaval.glo4002.atm_api.application.banking;

import ca.ulaval.glo4002.atm_api.application.ServiceLocator;
import ca.ulaval.glo4002.atm_api.application.jpa.EntityManagerProvider;
import ca.ulaval.glo4002.atm_api.domain.accounts.Account;
import ca.ulaval.glo4002.atm_api.domain.accounts.AccountRepository;
import ca.ulaval.glo4002.atm_api.domain.accounts.transactions.TransactionLog;
import ca.ulaval.glo4002.atm_api.rest.TransferRequest;

public class BankingService {

    private AccountRepository accountRepository;
    private EntityManagerProvider entityManagerProvider;

    public BankingService() {
        this.accountRepository = ServiceLocator.resolve(AccountRepository.class);
        this.entityManagerProvider = new EntityManagerProvider();
    }

    public BankingService(AccountRepository accountRepository, EntityManagerProvider provider) {
        this.accountRepository = accountRepository;
        this.entityManagerProvider = provider;
    }

    public TransactionLog transferMoney(int accountNumber, int recipientAccountNumber, TransferRequest transferRequest) {
        return entityManagerProvider.executeInTransaction(() -> {
            Account fromAccount = accountRepository.findByNumber(accountNumber);
            Account recipientAccount = accountRepository.findByNumber(recipientAccountNumber);
            
            TransactionLog log = fromAccount.transferMoneyTo(recipientAccount, transferRequest.amount);
            
            accountRepository.persist(fromAccount);
            accountRepository.persist(recipientAccount);
            
            return log;
        });
    }
}
