package com.netcracker.komarov.dao.entity;

import java.util.Objects;

public class Account extends BaseEntity {
    private double balance;
    private boolean locked;
    private long clientId;

    public Account() {
    }

    public Account(long id, double balance, boolean locked, long clientId) {
        super(id);
        this.balance = balance;
        this.locked = locked;
        this.clientId = clientId;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Account account = (Account) o;
        return Double.compare(account.balance, balance) == 0 &&
                locked == account.locked &&
                clientId == account.clientId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), balance, locked, clientId);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                ", locked=" + locked +
                ", clientId=" + clientId +
                '}';
    }
}
