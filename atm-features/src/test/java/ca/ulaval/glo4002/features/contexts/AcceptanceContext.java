package ca.ulaval.glo4002.features.contexts;

import ca.ulaval.glo4002.atm_api.application.ServiceLocator;
import ca.ulaval.glo4002.atm_api.application.banking.BankingService;
import ca.ulaval.glo4002.atm_api.contexts.Context;
import ca.ulaval.glo4002.atm_api.domain.accounts.AccountRepository;
import ca.ulaval.glo4002.atm_api.infrastructure.persistence.HibernateAccountRepository;

public class AcceptanceContext implements Context {

    @Override
    public void apply() {
        ServiceLocator.reset();
        ServiceLocator.registerSingleton(AccountRepository.class, new HibernateAccountRepository());
        ServiceLocator.registerSingleton(BankingService.class, new BankingService());
    }
}
