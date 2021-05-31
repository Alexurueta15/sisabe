package edu.utez.sisabe.util;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    //Pasamos por parametro: destinatario, asunto y el mensaje
    public void sendEmail(String to, String content) throws MessagingException {
       MimeMessage message = mailSender.createMimeMessage();
       MimeMessageHelper helper = new MimeMessageHelper(message);
       helper.setTo(to);
       helper.setSubject("SISABE");
       helper.setText("Bienvenido al sistema SISABE, para ingresar use la contraseña: " + content);
       mailSender.send(message);
    }
}
