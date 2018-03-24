package dev3.bank.entity;

import java.util.Collection;

public class UnlockRequestAccount {
    public Collection<Account> getAccountCollection() {
        return accountCollection;
    }

    public void setAccountCollection(Collection<Account> accountCollection) {
        this.accountCollection = accountCollection;
    }

    private Collection<Account> accountCollection;
}
