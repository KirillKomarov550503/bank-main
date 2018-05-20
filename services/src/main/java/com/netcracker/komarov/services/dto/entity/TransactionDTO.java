package com.netcracker.komarov.services.dto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Objects;

public class TransactionDTO implements Serializable {
    private long id;
    private long accountFromId;
    private long accountToId;
    private double money;
    private String date;

    public TransactionDTO() {
    }

    public TransactionDTO(long id, long accountFromId, long accountToId, double money, String date) {
        this.id = id;
        this.accountFromId = accountFromId;
        this.accountToId = accountToId;
        this.money = money;
        this.date = date;
    }

    @ApiModelProperty(readOnly = true, hidden = true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public long getId() {
        return id;
    }

    @JsonIgnore
    public void setId(long id) {
        this.id = id;
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

    @ApiModelProperty(readOnly = true, hidden = true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public String getDate() {
        return date;
    }

    @JsonIgnore
    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionDTO dto = (TransactionDTO) o;
        return id == dto.id &&
                accountFromId == dto.accountFromId &&
                accountToId == dto.accountToId &&
                Double.compare(dto.money, money) == 0 &&
                Objects.equals(date, dto.date);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, accountFromId, accountToId, money, date);
    }

    @Override
    public String toString() {
        return "TransactionDTO{" +
                "id=" + id +
                ", accountFromId=" + accountFromId +
                ", accountToId=" + accountToId +
                ", money=" + money +
                ", date='" + date + '\'' +
                '}';
    }
}
