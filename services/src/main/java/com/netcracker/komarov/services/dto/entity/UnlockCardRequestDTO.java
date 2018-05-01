package com.netcracker.komarov.services.dto.entity;

public class UnlockCardRequestDTO extends AbstractDTO {
    private boolean locked;
    private int pin;
    private double balance;
    private long cardId;

    public UnlockCardRequestDTO() {
    }

    public UnlockCardRequestDTO(boolean locked, int pin, double balance, long cardId) {
        this.locked = locked;
        this.pin = pin;
        this.balance = balance;
        this.cardId = cardId;
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
}
