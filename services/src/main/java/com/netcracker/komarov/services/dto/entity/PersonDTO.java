package com.netcracker.komarov.services.dto.entity;

import java.io.Serializable;
import java.util.Objects;

public class PersonDTO implements Serializable {
    private long id;
    private String name;
    private String surname;
    private long phoneNumber;
    private long passportId;
    private String username;
    private String password;
    public PersonDTO() {
    }

    public PersonDTO(long id, String name, String surname, long phoneNumber,
                     long passportId, String username, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.passportId = passportId;
        this.username = username;
        this.password = password;
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

    public long getPassportId() {
        return passportId;
    }

    public void setPassportId(long passportId) {
        this.passportId = passportId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonDTO personDTO = (PersonDTO) o;
        return id == personDTO.id &&
                phoneNumber == personDTO.phoneNumber &&
                passportId == personDTO.passportId &&
                Objects.equals(name, personDTO.name) &&
                Objects.equals(surname, personDTO.surname) &&
                Objects.equals(username, personDTO.username);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, surname, phoneNumber, passportId, username);
    }

    @Override
    public String toString() {
        return "PersonDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", passportId=" + passportId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
