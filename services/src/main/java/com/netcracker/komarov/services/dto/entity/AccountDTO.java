package com.netcracker.komarov.services.dto.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDTO implements Serializable {
    @ApiModelProperty(readOnly = true, hidden = true)
    private Long id;
    private Boolean locked;
    private Double balance;
    private Long accountId;

    public AccountDTO() {
    }

    public AccountDTO(Boolean locked, Double balance) {
        this.locked = locked;
        this.balance = balance;
    }

    public AccountDTO(Long id, Boolean locked, Double balance, Long accountId) {
        this.id = id;
        this.locked = locked;
        this.balance = balance;
        this.accountId = accountId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDTO that = (AccountDTO) o;
        return Objects.equals(id, that.id) &&
                locked == that.locked &&
                Double.compare(that.balance, balance) == 0 &&
                Objects.equals(accountId, that.accountId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, locked, balance, accountId);
    }

    @Override
    public String toString() {
        return "AccountDTO{" +
                "id=" + id +
                ", locked=" + locked +
                ", balance=" + balance +
                ", accountId=" + accountId +
                '}';
    }
}
