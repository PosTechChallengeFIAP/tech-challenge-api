package com.tech.challenge.tech_challenge.core.domain.entities;

public enum EItemType {
    HAMBURGUER("hamburguer"),
    SNACK_ADITIONAL("snack_aditional"),
    SALAD("salad"),
    BREAD("bread");

    private String valor;

    EItemType(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}