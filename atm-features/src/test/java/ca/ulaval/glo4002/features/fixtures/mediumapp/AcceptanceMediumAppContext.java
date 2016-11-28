package ca.ulaval.glo4002.features.fixtures.mediumapp;

import java.util.function.Supplier;

import javax.persistence.EntityManager;

import ca.ulaval.glo4002.atm_api.application.ServiceLocator;
import ca.ulaval.glo4002.atm_api.application.banking.BankingService;
import ca.ulaval.glo4002.atm_api.application.jpa.EntityManagerProvider;
import ca.ulaval.glo4002.atm_api.application.jpa.ThreadLocalJpaEntityManagerProvider;
import ca.ulaval.glo4002.atm_api.contexts.Context;
import ca.ulaval.glo4002.atm_api.domain.accounts.AccountRepository;

public class AcceptanceMediumAppContext implements Context {

    @Override
    public void apply() {
        ServiceLocator.reset();

        FakeMapAccountRepository accountRepository = new FakeMapAccountRepository();
        EntityManagerProvider entityProvider = new EntityManagerProvider() {
            @Override
            public void executeInTransaction(Runnable transaction) {
                transaction.run();
            }

            @Override
            public <T> T executeInTransaction(Supplier<T> transaction) {
                return transaction.get();
            }
        };

        ServiceLocator.registerSingleton(AccountRepository.class, accountRepository);
        ServiceLocator.registerSingleton(BankingService.class, new BankingService(accountRepository, entityProvider));
    }
}
