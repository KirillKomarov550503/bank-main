package com.netcracker.komarov.dao.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "person")
public class Person extends BaseEntity {

    @Column(name = "name", length = 50)
    protected String name;

    @Column(name = "surname", length = 50)
    protected String surname;

    @Column(name = "phone_number")
    protected long phoneNumber;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    protected Role role;

    @Column(name = "login", length = 50)
    protected String login;

    @Column(name = "password", length = 50)
    protected String password;

    @Column(name = "passport_id")
    protected long passportId;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Admin admin;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Client client;

    public Person() {
    }

    public Person(String name, String surname, long phoneNumber, Role role,
                  String login, String password, long passportId,
                  Admin admin, Client client) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.login = login;
        this.password = password;
        this.passportId = passportId;
        this.admin = admin;
        this.client = client;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public long getPassportId() {
        return passportId;
    }

    public void setPassportId(long passportId) {
        this.passportId = passportId;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

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
