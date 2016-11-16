package ca.ulaval.glo4002.atm_api.domain.accounts;

public interface AccountRepository {

    Account findByNumber(int accountNumber);

    void persist(Account account);

}
