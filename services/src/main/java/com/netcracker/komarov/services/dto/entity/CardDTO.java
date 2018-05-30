package com.netcracker.komarov.services.dto.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardDTO implements Serializable {
    @ApiModelProperty(position = 1, readOnly = true, hidden = true)
    private Long id;

    @ApiModelProperty(position = 2, readOnly = true, hidden = true)
    private Boolean locked;

    @ApiModelProperty(position = 3, readOnly = true, hidden = true)
    private Double balance;

    @ApiModelProperty(position = 4, readOnly = true, hidden = true)
    private Long accountId;

    @ApiModelProperty(position = 5)
    private String pin;

    public CardDTO() {
    }

    public CardDTO(Long id, Boolean locked, Double balance, Long accountId, String pin) {
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

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
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
