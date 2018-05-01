package com.netcracker.komarov.services.dto.entity;

import java.io.Serializable;

public class TransactionDTO implements Serializable {
    private long accountFromId;
    private long accountToId;
    private double money;
    private String date;

    public TransactionDTO() {
    }

    public TransactionDTO(long accountFromId, long accountToId, double money, String date) {
        this.accountFromId = accountFromId;
        this.accountToId = accountToId;
        this.money = money;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
