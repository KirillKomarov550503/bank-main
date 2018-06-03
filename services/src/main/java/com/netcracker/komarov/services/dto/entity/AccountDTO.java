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
    private Long personId;

    public AccountDTO() {
    }

    public AccountDTO(Boolean locked, Double balance) {
        this.locked = locked;
        this.balance = balance;
    }

    public AccountDTO(Long id, Boolean locked, Double balance, Long personId) {
        this.id = id;
        this.locked = locked;
        this.balance = balance;
        this.personId = personId;
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

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDTO that = (AccountDTO) o;
        return Objects.equals(id, that.id) &&
                locked == that.locked &&
                Double.compare(that.balance, balance) == 0 &&
                Objects.equals(personId, that.personId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, locked, balance, personId);
    }

    @Override
    public String toString() {
        return "AccountDTO{" +
                "id=" + id +
                ", locked=" + locked +
                ", balance=" + balance +
                ", personId=" + personId +
                '}';
    }
}
