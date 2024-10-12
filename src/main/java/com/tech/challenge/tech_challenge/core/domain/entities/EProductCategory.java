package com.tech.challenge.tech_challenge.core.domain.entities;

public enum EProductCategory {
    SNAK("snak"),
    CUSTOM_SNAK("custom_snak"),
    DRINK("drink"),
    DESSERT("dessert");

    private String valor;

    EProductCategory(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}
