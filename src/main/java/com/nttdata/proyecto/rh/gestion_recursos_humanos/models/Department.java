package com.nttdata.proyecto.rh.gestion_recursos_humanos.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "head_id")
    @JsonIgnore
    private Employee head;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Employee getHead() {
        return head;
    }

    public void setHead(Employee head) {
        this.head = head;
    }
}
