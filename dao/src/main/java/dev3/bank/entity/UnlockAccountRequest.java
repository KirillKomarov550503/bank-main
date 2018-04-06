package dev3.bank.entity;

public class UnlockAccountRequest extends BaseEntity {
    private Account account;

    @Override
    public String toString() {
        return "UnlockAccountRequest{" +
                "account=" + account +
                ", id=" + id +
                '}';
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
