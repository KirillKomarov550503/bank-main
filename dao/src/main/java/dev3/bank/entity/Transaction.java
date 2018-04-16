package dev3.bank.entity;

import java.util.Objects;

public class Transaction extends BaseEntity {
    private String date;
    private long accountFromId;
    private long accountToId;
    private double money;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return accountFromId == that.accountFromId &&
                accountToId == that.accountToId &&
                Double.compare(that.money, money) == 0 &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {

        return Objects.hash(date, accountFromId, accountToId, money);
    }
}
