package com.tech.challenge.tech_challenge.core.application.util;

import com.tech.challenge.tech_challenge.core.application.exceptions.InvalidClientCPF;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CPFValidatorTest {

    @Test
    public void calcularDigitoTest(){
        assertEquals(CPFValidator.calcularDigito("078441200",10), 6);
        assertEquals(CPFValidator.calcularDigito("0784412006",11), 1);
        assertEquals(CPFValidator.calcularDigito("919012560",10), 6);
        assertEquals(CPFValidator.calcularDigito("9190125606",11), 5);
        assertEquals(CPFValidator.calcularDigito("286551930",10), 9);
        assertEquals(CPFValidator.calcularDigito("2865519309",11), 7);
    }

    @Test
    public void isCPFTest(){
        assertTrue(CPFValidator.isCPF("07844120061"));
        assertTrue(CPFValidator.isCPF("91901256065"));
        assertTrue(CPFValidator.isCPF("286.551.930-97"));
        assertFalse(CPFValidator.isCPF("07844120063"));
        assertFalse(CPFValidator.isCPF("0784412006"));
        assertTrue(CPFValidator.isCPF("078......44120061"));
        assertFalse(CPFValidator.isCPF("078.441.200-6"));
        assertFalse(CPFValidator.isCPF("adhdskjfhskj"));
        assertFalse(CPFValidator.isCPF(null));
        assertFalse(CPFValidator.isCPF("11111111111"));
        assertFalse(CPFValidator.isCPF("0784412"));
    }

    @Test
    public void formatCPFTest(){
        assertEquals(CPFValidator.formatCPF("07844120061"), "078.441.200-61");
        assertEquals(CPFValidator.formatCPF("078......44120061"), "078.441.200-61");
        assertEquals(CPFValidator.formatCPF("078.441.200-61"), "078.441.200-61");

        InvalidClientCPF ex = assertThrows(InvalidClientCPF.class, () -> {
            CPFValidator.formatCPF("07844120065");
        });

        assertEquals(ex.getMessage(), "The cpf 07844120065 is invalid");
    }
}
