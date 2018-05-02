package com.netcracker.komarov.services.dto.entity;

import java.io.Serializable;
import java.util.Objects;

public class UnlockAccountRequestDTO implements Serializable {
    private long id;
    private boolean locked;
    private double balance;
    private long accountId;

    public UnlockAccountRequestDTO() {
    }

    public UnlockAccountRequestDTO(long id, boolean locked, double balance, long accountId) {
        this.id = id;
        this.locked = locked;
        this.balance = balance;
        this.accountId = accountId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnlockAccountRequestDTO dto = (UnlockAccountRequestDTO) o;
        return id == dto.id &&
                locked == dto.locked &&
                Double.compare(dto.balance, balance) == 0 &&
                accountId == dto.accountId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, locked, balance, accountId);
    }

    @Override
    public String toString() {
        return "UnlockAccountRequestDTO{" +
                "id=" + id +
                ", locked=" + locked +
                ", balance=" + balance +
                ", accountId=" + accountId +
                '}';
    }
}
