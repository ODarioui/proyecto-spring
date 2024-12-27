package com.nttdata.proyecto.rh.gestion_recursos_humanos.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity(name = "users")
public class Users {

    @Id
    @GeneratedValue()
    private Long id;

}
