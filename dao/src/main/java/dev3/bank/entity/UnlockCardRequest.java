package dev3.bank.entity;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UnlockCardRequest request = (UnlockCardRequest) o;
        return cardId == request.cardId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), cardId);
    }

    public UnlockCardRequest(long id, long cardId) {

        super(id);
        this.cardId = cardId;
    }

    public UnlockCardRequest() {

    }

}
