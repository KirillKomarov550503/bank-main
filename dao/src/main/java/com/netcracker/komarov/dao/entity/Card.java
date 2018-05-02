package com.netcracker.komarov.dao.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "locked")
    private boolean locked;

    @Column(name = "pin")
    private int pin;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToOne(mappedBy = "card", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UnlockCardRequest unlockCardRequest;

    public Card() {

    }

    public Card(boolean locked, int pin, Account account) {
        this.locked = locked;
        this.pin = pin;
        this.account = account;
    }

    public UnlockCardRequest getUnlockCardRequest() {
        return unlockCardRequest;
    }

    public void setUnlockCardRequest(UnlockCardRequest unlockCardRequest) {
        this.unlockCardRequest = unlockCardRequest;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return id == card.id &&
                locked == card.locked &&
                pin == card.pin &&
                Objects.equals(account, card.account);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, locked, pin, account);
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", locked=" + locked +
                ", pin=" + pin +
                ", account=" + account +
                '}';
    }
}
