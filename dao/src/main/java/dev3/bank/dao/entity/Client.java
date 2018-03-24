package dev3.bank.dao.entity;

import java.util.Collection;

public class Client extends Person {
    private Collection<Account> accountCollection;

    public Collection<Account> getAccountCollection() {
        return accountCollection;
    }

    public void setAccountCollection(Collection<Account> accountCollection) {
        this.accountCollection = accountCollection;
    }
}
