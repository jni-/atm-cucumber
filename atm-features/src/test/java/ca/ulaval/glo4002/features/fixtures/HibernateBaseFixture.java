package ca.ulaval.glo4002.features.fixtures;

import java.util.function.Consumer;
import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import ca.ulaval.glo4002.atm_api.application.jpa.EntityManagerFactoryProvider;
import ca.ulaval.glo4002.atm_api.application.jpa.EntityManagerProvider;

public class HibernateBaseFixture {


    protected void withEntityManager(Consumer<EntityTransaction> action) {
        EntityManager entityManager = EntityManagerFactoryProvider.getFactory().createEntityManager();
        try {
            EntityManagerProvider.setEntityManager(entityManager);

            EntityTransaction transaction = entityManager.getTransaction();
            action.accept(transaction);
        } finally {
            entityManager.close();
        }
    }
    
    protected <T> T withEntityManager(Function<EntityTransaction, T> action) {
        EntityManager entityManager = EntityManagerFactoryProvider.getFactory().createEntityManager();
        try {
            EntityManagerProvider.setEntityManager(entityManager);

            EntityTransaction transaction = entityManager.getTransaction();
            return action.apply(transaction);
        } finally {
            entityManager.close();
        }
    }
}
