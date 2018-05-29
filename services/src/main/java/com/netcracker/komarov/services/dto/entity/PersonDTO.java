package com.netcracker.komarov.services.dto.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.netcracker.komarov.dao.entity.Role;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonDTO implements Serializable {
    @ApiModelProperty(position = 1, readOnly = true, hidden = true)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private String surname;

    @ApiModelProperty(position = 4)
    private Long phoneNumber;

    @ApiModelProperty(position = 5)
    private Long passportId;

    @ApiModelProperty(position = 6)
    private String username;

    @ApiModelProperty(position = 7)
    private String password;

    private Role role;

    public PersonDTO() {
    }

    public PersonDTO(long id, String name, String surname, Long phoneNumber,
                     Long passportId, String username, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.passportId = passportId;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
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

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getPassportId() {
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonDTO personDTO = (PersonDTO) o;
        return Objects.equals(id, personDTO.id) &&
                Objects.equals(phoneNumber, personDTO.phoneNumber) &&
                Objects.equals(passportId, personDTO.passportId) &&
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
