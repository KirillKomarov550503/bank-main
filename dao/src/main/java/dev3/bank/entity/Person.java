package dev3.bank.entity;

import java.util.Objects;

public class Person extends BaseEntity {
    protected String name;
    protected String surname;
    protected long phoneNumber;
    protected Role role;
    protected String login;
    protected String password;
    protected long passportId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Person person = (Person) o;
        return phoneNumber == person.phoneNumber &&
                passportId == person.passportId &&
                Objects.equals(name, person.name) &&
                Objects.equals(surname, person.surname) &&
                role == person.role &&
                Objects.equals(login, person.login) &&
                Objects.equals(password, person.password);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), name, surname, phoneNumber, role, login, password, passportId);
    }

    public Person() {

    }

    public Person(long id, String name, String surname, long phoneNumber,
                  Role role, String login, String password, long passportId) {
        super(id);
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.login = login;
        this.password = password;
        this.passportId = passportId;
    }

    public long getPassportId() {
        return passportId;
    }

    public void setPassportId(long passportId) {
        this.passportId = passportId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", role=" + role +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", passportId=" + passportId +
                ", id=" + id +
                '}';
    }

}
