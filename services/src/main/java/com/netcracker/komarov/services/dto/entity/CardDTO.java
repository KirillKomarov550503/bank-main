package com.netcracker.komarov.services.dto.entity;

import java.io.Serializable;

public class CardDTO implements Serializable {
    private long id;
    private boolean locked;
    private double balance;
    private long accountId;
    private int pin;

    public CardDTO() {
    }

    public CardDTO(long id, boolean locked, double balance, long accountId, int pin) {
        this.id = id;
        this.locked = locked;
        this.balance = balance;
        this.accountId = accountId;
        this.pin = pin;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "CardDTO{" +
                "id=" + id +
                ", locked=" + locked +
                ", balance=" + balance +
                ", accountId=" + accountId +
                ", pin=" + pin +
                '}';
    }
}