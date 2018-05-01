package com.netcracker.komarov.services.dto.entity;

import java.io.Serializable;

public class UnlockCardRequestDTO implements Serializable {
    private long id;
    private boolean locked;
    private int pin;
    private double balance;
    private long cardId;

    public UnlockCardRequestDTO() {
    }

    public UnlockCardRequestDTO(long id, boolean locked, int pin, double balance, long cardId) {
        this.id = id;
        this.locked = locked;
        this.pin = pin;
        this.balance = balance;
        this.cardId = cardId;
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

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public long getCardId() {
        return cardId;
    }

    public void setCardId(long cardId) {
        this.cardId = cardId;
    }

    @Override
    public String toString() {
        return "UnlockCardRequestDTO{" +
                "id=" + id +
                ", locked=" + locked +
                ", pin=" + pin +
                ", balance=" + balance +
                ", cardId=" + cardId +
                '}';
    }
}


