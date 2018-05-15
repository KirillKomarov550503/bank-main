package com.netcracker.komarov.dao.entity;

import java.util.Objects;

public class Admin extends BaseEntity {
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
        Admin admin = (Admin) o;
        return personId == admin.personId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), personId);
    }

    public Admin(){}

    @Override
    public String toString() {
        return "Admin{" +
                "personId=" + personId +
                ", id=" + id +
                '}';
    }

    public Admin(long id, long personId) {
        super(id);
        this.personId = personId;
    }

}
