package ca.ulaval.glo4002.atm_api.application.jpa;

import java.util.function.Supplier;

import javax.persistence.EntityManager;

public class EntityManagerProvider {

    private static ThreadLocal<EntityManager> localEntityManager = new ThreadLocal<>();

    public EntityManager getEntityManager() {
        return localEntityManager.get();
    }

    public void executeInTransaction(Runnable transaction) {
        localEntityManager.get().getTransaction().begin();
        transaction.run();
        localEntityManager.get().getTransaction().commit();
    }
    
    public <T> T executeInTransaction(Supplier<T> transaction) {
        localEntityManager.get().getTransaction().begin();
        T result = transaction.get();
        localEntityManager.get().getTransaction().commit();
        return result;
    }

    public static void setEntityManager(EntityManager entityManager) {
        localEntityManager.set(entityManager);
    }

    public static void clearEntityManager() {
        localEntityManager.set(null);
    }
}
