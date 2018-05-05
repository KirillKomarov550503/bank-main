package com.netcracker.komarov.dao.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "transaction")
public class Transaction extends BaseEntity {

    @Column(name = "date", length = 20)
    private String date;

    @Column(name = "from_id")
    private long accountFromId;

    @Column(name = "to_id")
    private long accountToId;

    @Column(name = "money")
    private double money;

    public Transaction() {
    }

    public Transaction(String date, long accountFromId, long accountToId, double money) {
        this.date = date;
        this.accountFromId = accountFromId;
        this.accountToId = accountToId;
        this.money = money;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Transaction that = (Transaction) o;
        return accountFromId == that.accountFromId &&
                accountToId == that.accountToId &&
                Double.compare(that.money, money) == 0 &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), date, accountFromId, accountToId, money);
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
