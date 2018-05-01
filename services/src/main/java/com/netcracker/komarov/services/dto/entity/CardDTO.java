package com.netcracker.komarov.services.dto.entity;

import java.io.Serializable;

public class CardDTO implements Serializable {
    private boolean locked;
    private long accountId;
    private double balance;
    private int pin;

    public CardDTO() {
    }

    public CardDTO(boolean locked, long accountId, double balance, int pin) {
        this.locked = locked;
        this.accountId = accountId;
        this.balance = balance;
        this.pin = pin;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
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

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }
}
