package com.netcracker.komarov.services.dto.entity;

import java.io.Serializable;

public class AccountDTO implements Serializable {
    private long id;
    private boolean locked;
    private double balance;

    public AccountDTO() {
    }

    public AccountDTO(boolean locked, double balance) {
        this.locked = locked;
        this.balance = balance;
    }

    public AccountDTO(long id, boolean locked, double balance) {
        this.id = id;
        this.locked = locked;
        this.balance = balance;
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

    @Override
    public String toString() {
        return "AccountDTO{" +
                "id=" + id +
                ", locked=" + locked +
                ", balance=" + balance +
                '}';
    }
}
