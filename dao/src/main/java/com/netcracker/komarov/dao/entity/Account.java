package com.netcracker.komarov.dao.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "account")
public class Account extends BaseEntity {

    @Column(name = "balance", columnDefinition = "DECIMAL")
    private double balance;

    @Column(name = "locked")
    private boolean locked;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Card> cards = new HashSet<>();

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Request request;

    public Account() {
    }

    public Account(double balance, boolean locked, Client client) {
        this.balance = balance;
        this.locked = locked;
        this.client = client;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Account account = (Account) o;
        return Double.compare(account.balance, balance) == 0 &&
                locked == account.locked &&
                Objects.equals(client, account.client);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), balance, locked, client);
    }

    @Override
    public String toString() {
        return "Account{" +
                "balance=" + balance +
                ", locked=" + locked +
                ", client=" + client +
                ", id=" + id +
                '}';
    }
}
