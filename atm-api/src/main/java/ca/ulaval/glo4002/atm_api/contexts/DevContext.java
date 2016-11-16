package ca.ulaval.glo4002.atm_api.contexts;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import ca.ulaval.glo4002.atm_api.application.ServiceLocator;
import ca.ulaval.glo4002.atm_api.application.banking.BankingService;
import ca.ulaval.glo4002.atm_api.application.jpa.EntityManagerFactoryProvider;
import ca.ulaval.glo4002.atm_api.application.jpa.EntityManagerProvider;
import ca.ulaval.glo4002.atm_api.domain.accounts.AccountRepository;
import ca.ulaval.glo4002.atm_api.domain.accounts.StandardAccount;
import ca.ulaval.glo4002.atm_api.infrastructure.persistence.HibernateAccountRepository;

public class DevContext implements Context {

    @Override
    public void apply() {
        ServiceLocator.registerSingleton(AccountRepository.class, new HibernateAccountRepository());
        ServiceLocator.registerSingleton(BankingService.class, new BankingService());

        fillDatabase();
    }

    private void fillDatabase() {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryProvider.getFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityManagerProvider.setEntityManager(entityManager);

        AccountRepository accountRepository = new HibernateAccountRepository();

        entityManager.getTransaction().begin();
        accountRepository.persist(new StandardAccount(123, 1000));
        accountRepository.persist(new StandardAccount(456, 1));
        entityManager.getTransaction().commit();

        EntityManagerProvider.clearEntityManager();
        entityManager.close();
    }

}
