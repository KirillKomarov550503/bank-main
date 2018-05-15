package com.netcracker.komarov.services.dto;

import java.io.Serializable;

public class TransactionDTO implements Serializable {
    private long accountFromId;
    private long accountToId;
    private double money;
    private long clientId;

    public TransactionDTO(){ }

    public TransactionDTO(long accountFromId, long accountToId, double money, long clientId) {
        this.accountFromId = accountFromId;
        this.accountToId = accountToId;
        this.money = money;
        this.clientId = clientId;
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
}
