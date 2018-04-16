package dev3.bank.entity;

import java.util.Objects;

public class UnlockAccountRequest extends BaseEntity {
    private long accountId;

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public UnlockAccountRequest(long id, long accountId) {
        super(id);
        this.accountId = accountId;
    }

    public UnlockAccountRequest() {

    }

    @Override
    public String toString() {
        return "UnlockAccountRequest{" +
                "accountId=" + accountId +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UnlockAccountRequest request = (UnlockAccountRequest) o;
        return accountId == request.accountId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), accountId);
    }
}
