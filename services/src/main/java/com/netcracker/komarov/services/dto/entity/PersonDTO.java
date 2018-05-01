package com.netcracker.komarov.services.dto.entity;

import java.io.Serializable;

public class PersonDTO implements Serializable {
    protected String name;
    protected String surname;
    protected long phoneNumber;
    protected long passportId;

    public PersonDTO() {
    }

    public PersonDTO(String name, String surname, long phoneNumber, long passportId) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
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

    public long getPassportId() {
        return passportId;
    }

    public void setPassportId(long passportId) {
        this.passportId = passportId;
    }
}
