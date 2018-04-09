package dev3.bank.entity;

public class Transaction extends BaseEntity {
    private String date;
    private long accountFromId;
    private long accountToId;
    private long money;

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

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

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
}
