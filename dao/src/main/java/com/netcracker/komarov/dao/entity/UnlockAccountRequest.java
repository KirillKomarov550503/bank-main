package com.netcracker.komarov.dao.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="unlockaccountrequest")
public class UnlockAccountRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name="account_id")
    private Account account;

    public UnlockAccountRequest() {
    }

    public UnlockAccountRequest(Account account) {
        this.account = account;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnlockAccountRequest that = (UnlockAccountRequest) o;
        return id == that.id &&
                Objects.equals(account, that.account);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, account);
    }

    @Override
    public String toString() {
        return "UnlockAccountRequest{" +
                "id=" + id +
                ", account=" + account +
                '}';
    }
}
