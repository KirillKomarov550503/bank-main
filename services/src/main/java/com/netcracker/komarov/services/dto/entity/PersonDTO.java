package com.netcracker.komarov.services.dto.entity;

import com.netcracker.komarov.dao.entity.Role;

import java.util.Objects;

public class PersonDTO extends AbstractDTO {
    protected String name;
    protected String surname;
    protected long phoneNumber;
    protected Role role;
    protected String login;
    protected String password;
    protected long passportId;

    public PersonDTO() {
    }

    public PersonDTO(String name, String surname,
                     long phoneNumber, Role role,
                     String login, String password, long passportId) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.login = login;
        this.password = password;
        this.passportId = passportId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonDTO personDTO = (PersonDTO) o;
        return phoneNumber == personDTO.phoneNumber &&
                passportId == personDTO.passportId &&
                Objects.equals(name, personDTO.name) &&
                Objects.equals(surname, personDTO.surname) &&
                role == personDTO.role &&
                Objects.equals(login, personDTO.login) &&
                Objects.equals(password, personDTO.password);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, surname, phoneNumber, role, login, password, passportId);
    }

    @Override
    public String toString() {
        return "PersonDTO{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", role=" + role +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", passportId=" + passportId +
                '}';
    }
}
