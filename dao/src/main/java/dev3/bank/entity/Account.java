package dev3.bank.entity;

public class Account extends BaseEntity {
    private double balance;
    private boolean locked;
    private Client client;

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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Account{" +
                "balance=" + balance +
                ", locked=" + locked +
                ", client=" + client +
                ", id=" + id +
                '}';
    }
}
