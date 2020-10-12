package br.com.joao.ourdigitalbank.utils;

import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;

import static org.junit.jupiter.api.Assertions.*;

class EmailConfigTest {

    @Test
    void sendEmail() throws MessagingException {
        String mail = "destinatário@gmail.com";
        String subject = "subject";
        String content = "content";

        EmailConfig emailConfig = new EmailConfig();

        emailConfig.sendEmail(content, subject, mail);
    }
}