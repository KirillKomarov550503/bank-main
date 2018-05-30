package com.netcracker.komarov.services.dto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.netcracker.komarov.dao.entity.Role;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminDTO implements Serializable {
    @ApiModelProperty(position = 1, readOnly = true, hidden = true)
    private long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private String surname;

    @ApiModelProperty(position = 6)
    private String login;

    @ApiModelProperty(position = 7)
    private String password;

    @ApiModelProperty(position = 4)
    private String phoneNumber;

    @ApiModelProperty(position = 5)
    private String passportId;

    @JsonIgnore
    private Role role;

    public AdminDTO() {
    }

    public AdminDTO(long id, String name, String surname, String login, String password,
                    String phoneNumber, String passportId, Role role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.passportId = passportId;
        this.role = role;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassportId() {
        return passportId;
    }

    public void setPassportId(String passportId) {
        this.passportId = passportId;
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
        AdminDTO adminDTO = (AdminDTO) o;
        return id == adminDTO.id &&
                Objects.equals(phoneNumber, adminDTO.phoneNumber) &&
                Objects.equals(passportId, adminDTO.passportId) &&
                Objects.equals(name, adminDTO.name) &&
                Objects.equals(surname, adminDTO.surname) &&
                Objects.equals(login, adminDTO.login) &&
                role == adminDTO.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, login, phoneNumber, passportId, role);
    }

    @Override
    public String toString() {
        return "AdminDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", passportId=" + passportId +
                ", role=" + role +
                '}';
    }
}
