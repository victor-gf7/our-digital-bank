package br.com.joao.ourdigitalbank.utils;

import br.com.joao.ourdigitalbank.model.client.Client;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidatorTest {

    @Test
    @DisplayName("Deveria validar email, cpf e idade maior de 18 anos")
    void test1() {
        Client client = new Client();
        client.setEmail("ferreirazaltan@gmail.com");
        client.setCpf("449.395.810-39");
        client.setDateBirth(LocalDate.of(1998,7,28));

        assertTrue(Validator.validateFormClient(client));
    }

    @Test
    @DisplayName("Não deveria passar email inválido, cpf e idade menor que 18 anos")
    void test2() {
        Client client = new Client();
        client.setEmail("ferreirazaltangmail.com");
        client.setCpf("449.395.810-39");
        client.setDateBirth(LocalDate.of(1998,7,28));

        assertFalse(Validator.validateFormClient(client));
    }

    @Test
    @DisplayName("Não deveria passar email inválido, cpf invalido e idade menor que 18 anos")
    void test3() {
        Client client = new Client();
        client.setEmail("ferreirazaltangmail.com");
        client.setCpf("111.111.111-11");
        client.setDateBirth(LocalDate.of(1998,7,28));

        assertFalse(Validator.validateFormClient(client));
    }

    @Test
    @DisplayName("Não deveria passar email inválido, cpf invalido e idade menor que 18 anos")
    void test4() {
        Client client = new Client();
        client.setEmail("ferreirazaltangmail.com");
        client.setCpf("111.111.111-11");
        client.setDateBirth(LocalDate.of(2010,7,28));

        assertFalse(Validator.validateFormClient(client));
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
    @DisplayName("Deveria validar idade maior que 18")
    void test5() {
        LocalDate birthDate = LocalDate.of(2010, 7, 28);
        LocalDate birthDateGreaterThan18 = LocalDate.of(1998, 7, 28);

        assertTrue(Validator.isValidAge(birthDateGreaterThan18));
        assertFalse(Validator.isValidAge(birthDate));
    }
}