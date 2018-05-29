package com.netcracker.komarov.services.dto.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.netcracker.komarov.dao.entity.Role;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDTO implements Serializable {
    @ApiModelProperty(position = 1, readOnly = true, hidden = true)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private String surname;

    @ApiModelProperty(position = 6)
    private String login;

    @ApiModelProperty(position = 7)
    private String password;

    @ApiModelProperty(position = 4)
    private Long phoneNumber;

    @ApiModelProperty(position = 5)
    private Long passportId;

    private Role role;

    public ClientDTO() {
    }

    public ClientDTO(long id, String name, String surname, String login, String password,
                     Long phoneNumber, Long passportId, Role role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.passportId = passportId;
        this.role = role;
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
        return Objects.equals(id, clientDTO.id) &&
                Objects.equals(phoneNumber, clientDTO.phoneNumber) &&
                Objects.equals(passportId, clientDTO.passportId) &&
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

