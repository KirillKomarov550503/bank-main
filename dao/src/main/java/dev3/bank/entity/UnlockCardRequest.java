package dev3.bank.entity;

public class UnlockCardRequest extends BaseEntity{
    private long cardId;

    public long getCardId() {
        return cardId;
    }

    public void setCardId(long cardId) {
        this.cardId = cardId;
    }

    @Override
    public String toString() {
        return "UnlockCardRequest{" +
                "cardId=" + cardId +
                ", id=" + id +
                '}';
    }
}
