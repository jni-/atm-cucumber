package ca.ulaval.glo4002.atm_api.application.jpa;

import java.util.function.Supplier;

import javax.persistence.EntityManager;

public class EntityManagerProviderForTests extends EntityManagerProvider {

    @Override
    public void executeInTransaction(Runnable transaction) {
        transaction.run();
    }
    
    @Override
    public <T> T executeInTransaction(Supplier<T> transaction) {
        return transaction.get();
    }

    @Override
    public EntityManager getEntityManager() {
        throw new RuntimeException("Unit tests should not use the entity manager");
    }

}
