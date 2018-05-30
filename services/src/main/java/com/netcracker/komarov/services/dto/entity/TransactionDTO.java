package com.netcracker.komarov.services.dto.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDTO implements Serializable {
    @ApiModelProperty(readOnly = true, hidden = true)
    private Long id;
    private String accountFromId;
    private String accountToId;
    private String money;
    @ApiModelProperty(readOnly = true, hidden = true)
    private String date;

    public TransactionDTO() {
    }

    public TransactionDTO(Long id, String accountFromId, String accountToId, String money, String date) {
        this.id = id;
        this.accountFromId = accountFromId;
        this.accountToId = accountToId;
        this.money = money;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountFromId() {
        return accountFromId;
    }

    public void setAccountFromId(String accountFromId) {
        this.accountFromId = accountFromId;
    }

    public String getAccountToId() {
        return accountToId;
    }

    public void setAccountToId(String accountToId) {
        this.accountToId = accountToId;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
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
