package entity;

public class UnlockCardRequest extends BaseEntity{
    private Card card;

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
