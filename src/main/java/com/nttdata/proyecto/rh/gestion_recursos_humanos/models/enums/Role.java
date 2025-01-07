package com.nttdata.proyecto.rh.gestion_recursos_humanos.models.enums;

public enum Role {
    ADMIN(4),
    EMPLOYEE(3),
    HR(2),
    USER(1);

    private final int value;

    Role(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
