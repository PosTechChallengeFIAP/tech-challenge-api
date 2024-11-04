package com.tech.challenge.tech_challenge.core.application.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmailValidatorTest {

    @Test
    public void isValidEmailTest(){
        assertTrue(EmailValidator.isValidEmail("example@example.com"));
        assertTrue(EmailValidator.isValidEmail("example@example.com.br"));
        assertFalse(EmailValidator.isValidEmail("example@example"));
        assertFalse(EmailValidator.isValidEmail("example@.com"));
        assertFalse(EmailValidator.isValidEmail("example.com"));
        assertFalse(EmailValidator.isValidEmail("@example.com"));
        assertFalse(EmailValidator.isValidEmail("@example.com.br"));
        assertFalse(EmailValidator.isValidEmail(""));
        assertFalse(EmailValidator.isValidEmail(null));
    }

    @Test
    public void validateEmailTest(){
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
           EmailValidator.validateEmail("@example.com.br");
        });

        assertEquals(ex.getMessage(), "Invalid email address");
    }
}
