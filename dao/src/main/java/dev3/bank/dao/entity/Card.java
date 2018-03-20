package dev3.bank.dao.entity;

public class Card extends Id {
    private boolean locked;
    private int pin;
    private long accountId;

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean status) {
        this.locked = status;
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
}
