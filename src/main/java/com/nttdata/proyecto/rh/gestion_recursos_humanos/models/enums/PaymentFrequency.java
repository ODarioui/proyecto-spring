package com.nttdata.proyecto.rh.gestion_recursos_humanos.models.enums;

public enum PaymentFrequency {
    DAYLY(1),
    WEEKLY(2),
    MONTHLY(3),
    ANNUALLY(4);

    private final int value;

    PaymentFrequency(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
