package dev3.bank.entity;

public class Admin extends Person {
    private long personId;

    @Override
    public String toString() {
        return "Admin{" +
                "personId=" + personId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", role=" + role +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", id=" + id +
                '}';
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }
}
