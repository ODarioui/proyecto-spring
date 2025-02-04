package com.nttdata.proyecto.rh.gestion_recursos_humanos.models.enums;

public enum Role {
    ADMIN(4),
    HR(3),
    EMPLOYEE(2),
    USER(1);

    private final int value;

    // Constructor que inicializa ambos valores
    Role(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
