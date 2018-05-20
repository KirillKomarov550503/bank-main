package com.netcracker.komarov.services.dto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.netcracker.komarov.dao.entity.Role;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Objects;

public class ClientDTO implements Serializable {
    @ApiModelProperty(position = 1)
    private long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private String surname;

    @ApiModelProperty(position = 6)
    private String login;

    @ApiModelProperty(position = 7)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @ApiModelProperty(position = 4)
    private long phoneNumber;

    @ApiModelProperty(position = 5)
    private long passportId;

    @JsonIgnore
    private Role role;

    public ClientDTO() {
    }

    public ClientDTO(long id, String name, String surname, String login, String password, long phoneNumber, long passportId, Role role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.passportId = passportId;
        this.role = role;
    }

    @ApiModelProperty(readOnly = true, hidden = true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public long getId() {
        return id;
    }

    @JsonIgnore
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
        ClientDTO clientDTO = (ClientDTO) o;
        return id == clientDTO.id &&
                phoneNumber == clientDTO.phoneNumber &&
                passportId == clientDTO.passportId &&
                Objects.equals(name, clientDTO.name) &&
                Objects.equals(surname, clientDTO.surname) &&
                Objects.equals(login, clientDTO.login) &&
                role == clientDTO.role;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, surname, login, phoneNumber, passportId, role);
    }

    @Override
    public String toString() {
        return "ClientDTO{" +
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

