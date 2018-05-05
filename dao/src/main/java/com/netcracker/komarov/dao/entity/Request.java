package com.netcracker.komarov.dao.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Objects;

@Entity
public class Request extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToOne
    @JoinColumn(name = "card_id")
    private Card card;


    public Request() {
    }

    public Request(Account account, Card card) {
        this.account = account;
        this.card = card;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Request request = (Request) o;
        return Objects.equals(account, request.account) &&
                Objects.equals(card, request.card);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), account, card);
    }

    @Override
    public String toString() {
        return "Request{" +
                "account=" + account +
                ", card=" + card +
                ", id=" + id +
                '}';
    }
}
