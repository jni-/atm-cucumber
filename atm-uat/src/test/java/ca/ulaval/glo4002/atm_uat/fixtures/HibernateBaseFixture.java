package ca.ulaval.glo4002.atm_uat.fixtures;

import java.util.function.Supplier;

import javax.persistence.EntityManager;

import ca.ulaval.glo4002.atm_api.application.jpa.EntityManagerFactoryProvider;
import ca.ulaval.glo4002.atm_api.application.jpa.EntityManagerProvider;

public class HibernateBaseFixture {


    protected void withEntityManager(Runnable action) {
        EntityManager entityManager = EntityManagerFactoryProvider.getFactory().createEntityManager();
        try {
            EntityManagerProvider.setEntityManager(entityManager);

            entityManager.getTransaction().begin();
            action.run();
            entityManager.getTransaction().commit();

        } finally {
            entityManager.close();
        }
    }
    
    protected <T> T withEntityManager(Supplier<T> action) {
        EntityManager entityManager = EntityManagerFactoryProvider.getFactory().createEntityManager();
        try {
            EntityManagerProvider.setEntityManager(entityManager);

            entityManager.getTransaction().begin();
            T result = action.get();
            entityManager.getTransaction().commit();
            return result;

        } finally {
            entityManager.close();
        }
    
    }
}
