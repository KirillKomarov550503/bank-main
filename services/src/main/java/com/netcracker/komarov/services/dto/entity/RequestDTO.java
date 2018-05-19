package com.netcracker.komarov.services.dto.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestDTO implements Serializable {
    private long id;
    private AccountDTO accountDTO;
    private CardDTO cardDTO;

    public RequestDTO() {
    }

    public RequestDTO(long id, AccountDTO accountDTO, CardDTO cardDTO) {
        this.id = id;
        this.accountDTO = accountDTO;
        this.cardDTO = cardDTO;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AccountDTO getAccountDTO() {
        return accountDTO;
    }

    public void setAccountDTO(AccountDTO accountDTO) {
        this.accountDTO = accountDTO;
    }

    public CardDTO getCardDTO() {
        return cardDTO;
    }

    public void setCardDTO(CardDTO cardDTO) {
        this.cardDTO = cardDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestDTO that = (RequestDTO) o;
        return id == that.id &&
                Objects.equals(accountDTO, that.accountDTO) &&
                Objects.equals(cardDTO, that.cardDTO);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, accountDTO, cardDTO);
    }

    @Override
    public String toString() {
        return "RequestDTO{" +
                "id=" + id +
                ", accountDTO=" + accountDTO +
                ", cardDTO=" + cardDTO +
                '}';
    }
}

