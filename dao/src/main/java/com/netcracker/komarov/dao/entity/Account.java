package com.netcracker.komarov.dao.entity;

import java.util.Objects;

public class Account extends BaseEntity {
    private double balance;
    private boolean locked;
    private long clientId;
    private long accountId;

    public Account() {
    }

    public Account(long id, double balance, boolean locked, long clientId, long accountId) {
        super(id);
        this.balance = balance;
        this.locked = locked;
        this.clientId = clientId;
        this.accountId = accountId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "Account{" +
                "balance=" + balance +
                ", locked=" + locked +
                ", clientId=" + clientId +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Account account = (Account) o;
        return Double.compare(account.balance, balance) == 0 &&
                locked == account.locked &&
                clientId == account.clientId &&
                accountId == account.accountId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), balance, locked, clientId, accountId);
    }
}
