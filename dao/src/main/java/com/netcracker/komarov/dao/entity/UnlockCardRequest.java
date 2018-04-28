package com.netcracker.komarov.dao.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "unlockcardrequest")
public class UnlockCardRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    @JoinColumn(name="card_id")
    private Card card;

    public UnlockCardRequest() {
    }

    public UnlockCardRequest(Card card) {
        this.card = card;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        UnlockCardRequest that = (UnlockCardRequest) o;
        return id == that.id &&
                Objects.equals(card, that.card);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, card);
    }

    @Override
    public String toString() {
        return "UnlockCardRequest{" +
                "id=" + id +
                ", card=" + card +
                '}';
    }
}
