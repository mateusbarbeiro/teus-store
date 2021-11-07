package com.teusstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public boolean sendEmail(String destinatario, String assunto, String mensagemCorpo) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("testelojaonlinepw@gmail.com");
            message.setTo(destinatario);
            message.setSubject(assunto);
            message.setText(mensagemCorpo);
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
