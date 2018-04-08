package dev3.bank.entity;

public class Account extends BaseEntity {
    private double balance;
    private boolean locked;
    private long clientId;

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    private long accountId;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "Account{" +
                "balance=" + balance +
                ", locked=" + locked +
                ", clientId=" + clientId +
                ", accountId=" + accountId +
                ", id=" + id +
                '}';
    }
}
