package br.com.joao.ourdigitalbank.utils;



import br.com.joao.ourdigitalbank.model.account.Account;
import br.com.joao.ourdigitalbank.model.enumeration.StatusProposalEnum;
import br.com.joao.ourdigitalbank.model.proposal.Proposal;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailConfig {


    private Session createSessionMail() {
        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", 465);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.port", 465);

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("youremail@gmail.com", "yourpassword");
            }
        });

        session.setDebug(true);

        return session;
    }

    public void sendEmail(String content, String subject, String mail) throws AddressException, MessagingException {


        String sender = "sender@gmail.com";
        System.out.println("__________________________________________________");
        System.out.println("sending email from: " + sender + " to: " + mail);
        System.out.println("subject: " + subject);

        Message message = new MimeMessage(createSessionMail());
        message.setFrom(new InternetAddress(sender)); // Remetente

        Address[] toUser = InternetAddress // Destinatário(s)
                .parse(mail.trim().toLowerCase());

        message.setRecipients(Message.RecipientType.TO, toUser);
        message.setSubject(subject);// Assunto
        message.setContent(content, "text/html");
        Transport.send(message);

        System.out.println("Email successfully sent!");
        System.out.println("__________________________________________________");

    }

    public void sendEmailFake(String content, String subject, String mail){

        String sender = "SuporteOurDigitalBank@gmail.com";
        System.out.println("__________________________________________________");
        System.out.println("sending email from: " + sender + " to: " + mail);
        System.out.println("subject: " + subject);


        System.out.println(content);
        System.out.println("Email successfully sent!");
        System.out.println("__________________________________________________");

    }

    public String generateContent(Account account){
        String content = null;

        if (account.getProposal().getAccept().equals(StatusProposalEnum.LIBERATED)){
            content = "Congratulations " +  account.getProposal().getClient().getFirstName()+ "!\n" +
                    "Your account has been created and you have just become a member of OurDigitalBank, we are very happy to have you with us.\n" +
                    "Below are your account details:\n" +
                    "Agency: " + account.getAgencyNumber() + "\n" +
                    "Account: " + account.getAccountNumber() + "\n" +
                    "BankNumber: "+ account.getBankCode() + "\n" +
                    "Your credit card will be sent to the address registered in the proposal: \n" +
                    "Street: " + account.getProposal().getClient().getAddress().getStreet() +"\n" +
                    "Neighborhood: "+ account.getProposal().getClient().getAddress().getNeighborhood() + "\n" +
                    "City: " + account.getProposal().getClient().getAddress().getCity() + "\n" +
                    "Complement: "+ account.getProposal().getClient().getAddress().getComplement()+"\n" +
                    "Welcome to OurDigitalBank and let's make history together!";
        }

        return content;
    }

    public String generateContent(Proposal proposal){
        return "Hello " +  proposal.getClient().getFirstName()+ "!\n" +
                "Are you sure you want to decline the proposal for a new account at OurDigitalBank?\n" +
                "enter the site and check out our benefits. " +
                "If you want to change your mind just click here.";
    }
}
