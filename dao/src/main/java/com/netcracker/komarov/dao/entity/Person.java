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
    protected String phoneNumber;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    protected Role role;

    @Column(name = "username", length = 50)
    protected String username;

    @Column(name = "password", length = 100)
    protected String password;

    @Column(name = "passport_id")
    protected String passportId;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Admin admin;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Client client;

    public Person() {
    }

    public Person(String name, String surname, String phoneNumber, Role role,
                  String username, String password, String passportId,
                  Admin admin, Client client) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.username = username;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String login) {
        this.username = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassportId() {
        return passportId;
    }

    public void setPassportId(String passportId) {
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
        return Objects.equals(phoneNumber, person.phoneNumber) &&
                Objects.equals(passportId, person.passportId) &&
                Objects.equals(name, person.name) &&
                Objects.equals(surname, person.surname) &&
                role == person.role &&
                Objects.equals(username, person.username) &&
                Objects.equals(password, person.password);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), name, surname, phoneNumber, role, username, password, passportId);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", role=" + role +
                ", login='" + username + '\'' +
                ", password='" + password + '\'' +
                ", passportId=" + passportId +
                ", id=" + id +
                '}';
    }
}
