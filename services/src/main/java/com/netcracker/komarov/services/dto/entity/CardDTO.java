package com.netcracker.komarov.services.dto.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardDTO implements Serializable {
    @ApiModelProperty(readOnly = true, hidden = true)
    private Long id;
    private Boolean locked;
    private Double balance;
    private Long accountId;
    private Integer pin;

    public CardDTO() {
    }

    public CardDTO(Long id, Boolean locked, Double balance, Long accountId, Integer pin) {
        this.id = id;
        this.locked = locked;
        this.balance = balance;
        this.accountId = accountId;
        this.pin = pin;
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

    public Integer getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
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
        CardDTO cardDTO = (CardDTO) o;
        return Objects.equals(id, cardDTO.id) &&
                locked == cardDTO.locked &&
                Double.compare(cardDTO.balance, balance) == 0 &&
                Objects.equals(accountId, cardDTO.accountId) &&
                Objects.equals(pin, cardDTO.pin);
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
