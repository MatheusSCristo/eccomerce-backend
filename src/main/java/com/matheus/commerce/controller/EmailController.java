package com.matheus.commerce.controller;

import com.matheus.commerce.domain.Email;
import com.matheus.commerce.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity<Void> sendVerificationEmail(@RequestBody Email email){
        emailService.sendVerificationEmail(email);
        return ResponseEntity.ok().build();
    }


}
