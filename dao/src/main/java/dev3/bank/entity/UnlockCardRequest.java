package dev3.bank.entity;

public class UnlockCardRequest extends BaseEntity{
    private Card card;

    @Override
    public String toString() {
        return "UnlockCardRequest{" +
                "card=" + card +
                ", id=" + id +
                '}';
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
