package com.nttdata.proyecto.rh.gestion_recursos_humanos.models.dtos;

public class UserStatusDto {

    private String status;
    private Long id;
    private String username;

    public UserStatusDto(String status, Long id, String username) {
        this.status = status;
        this.id = id;
        this.username = username;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

}
