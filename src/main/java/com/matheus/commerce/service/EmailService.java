package com.matheus.commerce.service;

import com.matheus.commerce.domain.Email;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${mail.button.link}")
    private String verifyButtonLink;

    public void sendVerificationEmail(Email email) {
        String html = "<div style=`display:flex;flex-direction:column;gap:10px`>" +
                "<h1>Olá</h1>" + email.getUserName() + "<h1>, você está quase completando o processo de cadastro no nosso Commerce</h1>" +
                "<h2>Se não foi você que realizou o cadastro, desconsidere esse email.</h2>" +
                "<a href=" + verifyButtonLink + ">Confirmar cadastro</a>" +
                "</div>";
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            message.setSubject("Verificação de conta");
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("noreply@email.com");
            helper.setTo(email.getEmailTo());
            helper.setText("Text",html);
            javaMailSender.send(message);
        } catch (MessagingException exception) {
            throw new RuntimeException();
        }
    }

    public void verifyEmail() {

    }


}
