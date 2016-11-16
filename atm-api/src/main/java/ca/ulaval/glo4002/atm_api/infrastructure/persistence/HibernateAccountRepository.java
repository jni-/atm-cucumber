package ca.ulaval.glo4002.atm_api.infrastructure.persistence;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import ca.ulaval.glo4002.atm_api.application.jpa.EntityManagerProvider;
import ca.ulaval.glo4002.atm_api.domain.accounts.Account;
import ca.ulaval.glo4002.atm_api.domain.accounts.AccountNotFoundException;
import ca.ulaval.glo4002.atm_api.domain.accounts.AccountRepository;

public class HibernateAccountRepository implements AccountRepository {

    @Override
    public Account findByNumber(int accountNumber) {
        EntityManager entityManager = new EntityManagerProvider().getEntityManager();
        TypedQuery<Account> query = entityManager.createQuery("from Account a where a.accountNumber = :accountNumber", Account.class);
        query.setParameter("accountNumber", accountNumber);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            throw new AccountNotFoundException();
        }
    }

    @Override
    public void persist(Account account) {
        EntityManager entityManager = new EntityManagerProvider().getEntityManager();
        entityManager.persist(account);
    }

}
