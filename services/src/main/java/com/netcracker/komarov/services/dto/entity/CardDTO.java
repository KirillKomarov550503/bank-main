package com.netcracker.komarov.services.dto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Objects;

public class CardDTO implements Serializable {
    private long id;
    private boolean locked;
    private double balance;
    private long accountId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int pin;

    public CardDTO() {
    }

    public CardDTO(long id, boolean locked, double balance, long accountId, int pin) {
        this.id = id;
        this.locked = locked;
        this.balance = balance;
        this.accountId = accountId;
        this.pin = pin;
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

    @ApiModelProperty(readOnly = true, hidden = true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public boolean isLocked() {
        return locked;
    }

    @JsonIgnore
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @ApiModelProperty(readOnly = true, hidden = true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public double getBalance() {
        return balance;
    }

    @JsonIgnore
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    @ApiModelProperty(readOnly = true, hidden = true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public long getAccountId() {
        return accountId;
    }

    @JsonIgnore
    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardDTO cardDTO = (CardDTO) o;
        return id == cardDTO.id &&
                locked == cardDTO.locked &&
                Double.compare(cardDTO.balance, balance) == 0 &&
                accountId == cardDTO.accountId &&
                pin == cardDTO.pin;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, locked, balance, accountId, pin);
    }

    @Override
    public String toString() {
        return "CardDTO{" +
                "id=" + id +
                ", locked=" + locked +
                ", balance=" + balance +
                ", accountId=" + accountId +
                ", pin=" + pin +
                '}';
    }
}
