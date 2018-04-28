package com.netcracker.komarov.services.dto.entity;

import java.util.Objects;

public class TransactionDTO extends AbstractDTO {
    private long accountFromId;
    private long accountToId;
    private double money;
    private long clientId;
    private String date;

    public TransactionDTO() {
    }

    public TransactionDTO(long accountFromId, long accountToId, double money, long clientId, String date) {
        this.accountFromId = accountFromId;
        this.accountToId = accountToId;
        this.money = money;
        this.clientId = clientId;
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

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionDTO that = (TransactionDTO) o;
        return accountFromId == that.accountFromId &&
                accountToId == that.accountToId &&
                Double.compare(that.money, money) == 0 &&
                clientId == that.clientId &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {

        return Objects.hash(accountFromId, accountToId, money, clientId, date);
    }

    @Override
    public String toString() {
        return "TransactionDTO{" +
                "accountFromId=" + accountFromId +
                ", accountToId=" + accountToId +
                ", money=" + money +
                ", clientId=" + clientId +
                ", date='" + date + '\'' +
                '}';
    }
}
