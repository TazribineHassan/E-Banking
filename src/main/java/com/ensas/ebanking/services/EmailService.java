package com.ensas.ebanking.services;

import com.sun.mail.smtp.SMTPTransport;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

import static com.ensas.ebanking.constant.EmailConstants.*;
import static javax.mail.Message.RecipientType.CC;
import static javax.mail.Message.RecipientType.TO;


@Service
public class EmailService {

    public void sendNewPasswordEmail(String firstName, String username, String password, String email) throws MessagingException {
        Message message = createEmail(firstName, username, password, email);
        SMTPTransport transport = (SMTPTransport) getEmailSession().getTransport(SIMPLE_MAIL_TRANSFER_PROTOCOL);
        transport.connect(GMAIL_SMTP_SERVER, USERNAME, PASSWORD);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    public void sendFoundRecievedEmail(String firstName, double amount, String email) throws MessagingException {
        String text = "Hello " + firstName + "\n \n account account has recieved : " + amount + " Dh\n \n The Support team";
        Message message = createEmail(text, FOUND_RECIEVE_EMAIL_SUBJECT, email);
        SMTPTransport transport = (SMTPTransport) getEmailSession().getTransport(SIMPLE_MAIL_TRANSFER_PROTOCOL);
        transport.connect(GMAIL_SMTP_SERVER, USERNAME, PASSWORD);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    public void sendConfirmationEmail(String full_name, String verificationCode, String email) throws MessagingException {

        String text = "Hello " + full_name + "\n \n Verification Code: " + verificationCode + "\n \n The Support team";
        Message message = createEmail(text, TRANSACTION_CONFIRMATION_EMAIL_SUBJECT, email);
        SMTPTransport transport = (SMTPTransport) getEmailSession().getTransport(SIMPLE_MAIL_TRANSFER_PROTOCOL);
        transport.connect(GMAIL_SMTP_SERVER, USERNAME, PASSWORD);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    private Message createEmail(String firstName, String username,  String password, String email) throws MessagingException {
        Message message = new MimeMessage(getEmailSession());
        message.setFrom(new InternetAddress(FROM_EMAIL));
        message.setRecipients(TO, InternetAddress.parse(email, false));
        message.setRecipients(CC, InternetAddress.parse(CC_EMAIL, false));
        message.setSubject(EMAIL_SUBJECT);
        message.setText("Hello " + firstName + "\n \n your new account credentials are: \nusername: " + username + "\npassword: " + password + "\n \n The Support team");
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }

    private Message createEmail(String text_message, String subject, String email) throws MessagingException {
        Message message = new MimeMessage(getEmailSession());
        message.setFrom(new InternetAddress(FROM_EMAIL));
        message.setRecipients(TO, InternetAddress.parse(email, false));
        message.setRecipients(CC, InternetAddress.parse(CC_EMAIL, false));
        message.setSubject(subject);
        message.setText(text_message);
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }

    private Session getEmailSession(){
        Properties properties = System.getProperties();
        properties.put(SMTP_HOST, GMAIL_SMTP_SERVER);
        properties.put(SMTP_AUTH, true);
        properties.put(SMTP_PORT, DEFAULT_PORT);
        properties.put(SMTP_STARTTLS_ENABLE, true);
        properties.put(SMTP_STARTTLS_REQUIRED, true);
        return Session.getInstance(properties, null);
    }

}
