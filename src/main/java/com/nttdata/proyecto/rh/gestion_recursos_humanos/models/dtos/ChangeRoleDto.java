package com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos;

import com.nttdata.proyecto.rh.gestion_recursos_humanos.models.enums.Role;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class ChangeRoleDto {

    private Long id;
    private String username;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
