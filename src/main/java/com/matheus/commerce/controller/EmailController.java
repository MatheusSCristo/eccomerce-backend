package com.matheus.commerce.controller;

import com.matheus.commerce.domain.Email;
import com.matheus.commerce.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("email")
public class EmailController {


    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity<Void> sendVerificationEmail(@RequestBody Email email) {
        emailService.sendVerificationEmail(email);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/")
    public ResponseEntity<Void> verifyEmail(@RequestParam("userEmail") String userEmail){
        emailService.verifyEmail(userEmail);
        return ResponseEntity.ok().build();

    }

}
