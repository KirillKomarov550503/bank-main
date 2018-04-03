package dev3.bank.entity;

public class UnlockAccountRequest extends BaseEntity {
    private long accountId;

    @Override
    public String toString() {
        return "UnlockAccountRequest{" +
                "accountId=" + accountId +
                ", id=" + id +
                '}';
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }
}
