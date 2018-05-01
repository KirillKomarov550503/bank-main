package com.netcracker.komarov.services.dto.entity;

import java.io.Serializable;

public class UnlockAccountRequestDTO implements Serializable {
    private boolean locked;
    private double balance;
    private long accountId;

    public UnlockAccountRequestDTO() {
    }

    public UnlockAccountRequestDTO(boolean locked, double balance, long accountId) {
        this.locked = locked;
        this.balance = balance;
        this.accountId = accountId;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }
}
