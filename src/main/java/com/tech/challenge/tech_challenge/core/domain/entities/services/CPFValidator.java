package com.tech.challenge.tech_challenge.core.domain.entities.services;

public class CPFValidator {
    public static boolean isCPF(String cpf) {
        cpf = cpf.replaceAll("\\D+", "");

        if (cpf.length() != 11) {
            return false;
        }

        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            int digito1 = calcularDigito(cpf.substring(0, 9), 10);
            int digito2 = calcularDigito(cpf.substring(0, 9) + digito1, 11);

            return cpf.equals(cpf.substring(0, 9) + digito1 + digito2);
        } catch (Exception e) {
            return false;
        }
    }

    private static int calcularDigito(String str, int peso) {
        int soma = 0;
        for (int i = 0; i < str.length(); i++) {
            int num = Integer.parseInt(str.substring(i, i + 1));
            soma += num * peso--;
        }
        int resto = 11 - (soma % 11);
        return (resto > 9) ? 0 : resto;
    }
}
