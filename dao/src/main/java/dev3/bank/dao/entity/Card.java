package dev3.bank.dao.entity;

public class Card extends BaseEntity {
    private boolean locked;
    private int pin;

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
}
