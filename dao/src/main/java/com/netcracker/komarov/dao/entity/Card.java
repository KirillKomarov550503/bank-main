package com.netcracker.komarov.dao.entity;

import java.util.Objects;

public class Card extends BaseEntity {
    private boolean locked;
    private int pin;
    private long accountId;

    public Card() {
    }

    public Card(long id, boolean locked, int pin, long accountId) {
        super(id);
        this.locked = locked;
        this.pin = pin;
        this.accountId = accountId;
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

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Card card = (Card) o;
        return locked == card.locked &&
                pin == card.pin &&
                accountId == card.accountId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), locked, pin, accountId);
    }

    @Override
    public String toString() {
        return "Card{" +
                "locked=" + locked +
                ", pin=" + pin +
                ", accountId=" + accountId +
                ", id=" + id +
                '}';
    }
}
