package com.nttdata.proyecto.rh.gestion_recursos_humanos.models.enums;

public enum PayrollStauts {
    PAID(1),
    ERRING(2),
    CANCELED(3),
    LATE(4);

    private final int value;

    PayrollStauts(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
