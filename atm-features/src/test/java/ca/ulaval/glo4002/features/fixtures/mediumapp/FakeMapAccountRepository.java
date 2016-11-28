package ca.ulaval.glo4002.features.fixtures.mediumapp;

import java.util.HashMap;
import java.util.Map;

import ca.ulaval.glo4002.atm_api.domain.accounts.Account;
import ca.ulaval.glo4002.atm_api.domain.accounts.AccountNotFoundException;
import ca.ulaval.glo4002.atm_api.domain.accounts.AccountRepository;

public class FakeMapAccountRepository implements AccountRepository {

    public Map<Integer, Account> store = new HashMap<>();

    @Override
    public Account findByNumber(int accountNumber) {
        Account account = store.get(accountNumber);

        if (account == null) {
            throw new AccountNotFoundException();
        }

        return account;
    }

    @Override
    public void persist(Account account) {
        store.put(account.getAccountNumber(), account);
    }

}
