package com.netcracker.komarov.dao.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "balance")
    private double balance;

    @Column(name = "locked")
    private boolean locked;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "account_id")
    private long accountId;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Card> cards = new HashSet<>();

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UnlockAccountRequest unlockAccountRequest;

    public Account() {
    }

    public Account(double balance, boolean locked, Client client, long accountId) {
        this.balance = balance;
        this.locked = locked;
        this.client = client;
        this.accountId = accountId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    public UnlockAccountRequest getUnlockAccountRequest() {
        return unlockAccountRequest;
    }

    public void setUnlockAccountRequest(UnlockAccountRequest unlockAccountRequest) {
        this.unlockAccountRequest = unlockAccountRequest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id &&
                Double.compare(account.balance, balance) == 0 &&
                locked == account.locked &&
                accountId == account.accountId &&
                Objects.equals(client, account.client);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, balance, locked, client, accountId);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                ", locked=" + locked +
                ", client=" + client +
                ", accountId=" + accountId +
                '}';
    }

}
