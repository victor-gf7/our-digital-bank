package br.com.joao.ourdigitalbank.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    @Test
    void validateFormClient() {
    }

    @Test
    void isValidEmailAddress() {
        String validEmail = "joao@gmail.com";
        String invalidEmail = "joao@gmhfghfhg";

        assertTrue(Validator.isValidEmailAddress(validEmail));
    }

    @Test
    void isValidCPF() {
        String invalidCPF = "888.989.884-77";

        assertFalse(Validator.isValidCPF(invalidCPF));
    }

    @Test
    void isValidAge() {
        String validAge = "28/07/1998";

        //assertTrue(Validator.isValidAge(validAge));
    }
}