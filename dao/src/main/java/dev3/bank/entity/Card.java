package dev3.bank.entity;

import java.util.Objects;

public class Card extends BaseEntity {
    private boolean locked;
    private int pin;
    private long accountId;
    private long cardId;

    public long getCardId() {
        return cardId;
    }

    public void setCardId(long cardId) {
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

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return locked == card.locked &&
                pin == card.pin &&
                accountId == card.accountId &&
                cardId == card.cardId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(locked, pin, accountId, cardId);
    }
}
