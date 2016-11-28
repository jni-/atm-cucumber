package ca.ulaval.glo4002.atm_api.application.jpa;

import java.util.function.Supplier;

import javax.persistence.EntityManager;

public interface EntityManagerProvider {

    void executeInTransaction(Runnable transaction);

    <T> T executeInTransaction(Supplier<T> transaction);

}
