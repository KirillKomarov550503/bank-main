package com.netcracker.komarov.services.dto.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDTO implements Serializable {
    @ApiModelProperty(readOnly = true, hidden = true)
    private Long id;
    private Long accountFromId;
    private Long accountToId;
    private Double money;
    @ApiModelProperty(readOnly = true, hidden = true)
    private String date;

    public TransactionDTO() {
    }

    public TransactionDTO(Long id, Long accountFromId, Long accountToId, Double money, String date) {
        this.id = id;
        this.accountFromId = accountFromId;
        this.accountToId = accountToId;
        this.money = money;
        this.date = date;
    }

    public TransactionDTO(Long id, Long accountFromId, Long accountToId, Double money) {
        this.id = id;
        this.accountFromId = accountFromId;
        this.accountToId = accountToId;
        this.money = money;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountFromId() {
        return accountFromId;
    }

    public void setAccountFromId(Long accountFromId) {
        this.accountFromId = accountFromId;
    }

    public Long getAccountToId() {
        return accountToId;
    }

    public void setAccountToId(Long accountToId) {
        this.accountToId = accountToId;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
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
        TransactionDTO dto = (TransactionDTO) o;
        return Objects.equals(id, dto.id) &&
                Objects.equals(accountFromId, dto.accountFromId) &&
                Objects.equals(accountToId, dto.accountToId) &&
                Objects.equals(money, dto.money) &&
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
