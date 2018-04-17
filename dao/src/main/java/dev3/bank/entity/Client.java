package dev3.bank.entity;

import java.util.Objects;

public class Client extends BaseEntity {
    private long personId;

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Client client = (Client) o;
        return personId == client.personId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), personId);
    }

    public Client() {
    }

    public Client(long id, long personId) {
        super(id);
        this.personId = personId;
    }

    @Override
    public String toString() {
        return "Client{" +
                "personId=" + personId +
                ", id=" + id +
                '}';
    }
}
