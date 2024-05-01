package com.matheus.commerce.service;

import com.matheus.commerce.domain.Email;
import com.matheus.commerce.domain.User;
import com.matheus.commerce.infra.exceptions.UserNotFoundException;
import com.matheus.commerce.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class EmailService {
    @Value("${spring.mail.username}")
    private String username;

    @Autowired
    private UserRepository userRepository;
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    public EmailService(JavaMailSender javaMailSender,SpringTemplateEngine springTemplateEngine){
        this.mailSender=javaMailSender;
        this.templateEngine=springTemplateEngine;
    }


    public void sendVerificationEmail(Email email)  {
        try{
        MimeMessage message = mailSender.createMimeMessage();
        Context context = new Context();
        context.setVariable("username", email.getUserName());
        context.setVariable("userEmail", email.getEmailTo());
        String process = templateEngine.process("email.html", context);
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        helper.setTo(email.getEmailTo());
        helper.setFrom(new InternetAddress(username));
        helper.setSubject("Email de verificação de cadastro");
        helper.setText(process,true);
        mailSender.send(message);
        }catch (MessagingException exception){
            throw new RuntimeException(exception.getMessage());
        }
    }

    public void verifyEmail(String userEmail){
        Optional<User> optionalUser=userRepository.findByEmail(userEmail);
        if(optionalUser.isEmpty()) throw new UserNotFoundException();
        User user= optionalUser.get();
        user.setVerifiedEmail(true);
        userRepository.save(user);
    }

}
