package com.matheus.commerce.controller;

import com.matheus.commerce.domain.Payment;
import com.matheus.commerce.dto.payment.PaymentDto;
import com.matheus.commerce.dto.payment.PaymentResponseDto;
import com.matheus.commerce.dto.payment.PaymentUpdateDto;
import com.matheus.commerce.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody PaymentDto paymentDto) {
        paymentService.create(paymentDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody PaymentUpdateDto paymentUpdateDto) {
        paymentService.update(paymentUpdateDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
