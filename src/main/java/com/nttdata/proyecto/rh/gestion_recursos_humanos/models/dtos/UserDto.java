package com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos;

import java.util.Date;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.enums.Role;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class UserDto {

    private String name;
    private String lastname1;
    private String lastname2;

    private String email;

    private String username;

    private String phone;
    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Date creationDate;

    private String status;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname1() {
        return this.lastname1;
    }

    public void setLastname1(String lastname1) {
        this.lastname1 = lastname1;
    }

    public String getLastname2() {
        return this.lastname2;
    }

    public void setLastname2(String lastname2) {
        this.lastname2 = lastname2;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
