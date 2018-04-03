package dev3.bank.entity;

public class Transaction extends BaseEntity {
    private String date;
    private long accountFromId;
    private long accountToId;
    private double money;

    @Override
    public String toString() {
        return "Transaction{" +
                "date='" + date + '\'' +
                ", accountFromId=" + accountFromId +
                ", accountToId=" + accountToId +
                ", money=" + money +
                ", id=" + id +
                '}';
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getAccountFromId() {
        return accountFromId;
    }

    public void setAccountFromId(long accountFromId) {
        this.accountFromId = accountFromId;
    }

    public long getAccountToId() {
        return accountToId;
    }

    public void setAccountToId(long accountToId) {
        this.accountToId = accountToId;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
