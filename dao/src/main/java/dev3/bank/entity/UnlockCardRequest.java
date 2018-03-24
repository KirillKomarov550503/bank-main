package dev3.bank.entity;

import java.util.Collection;

public class UnlockCardRequest extends BaseEntity{
    private Collection<Card> cardCollection;

    public Collection<Card> getCardCollection() {
        return cardCollection;
    }

    public void setCardCollection(Collection<Card> cardCollection) {
        this.cardCollection = cardCollection;
    }
}
