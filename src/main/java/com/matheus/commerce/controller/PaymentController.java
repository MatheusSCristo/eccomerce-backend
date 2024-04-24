package com.matheus.commerce.controller;

import com.matheus.commerce.dto.payment.PaymentDto;
import com.matheus.commerce.dto.payment.PaymentUpdateDto;
import com.matheus.commerce.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("payment")
@AllArgsConstructor
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid PaymentDto paymentDto) {
        paymentService.create(paymentDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody @Valid PaymentUpdateDto paymentUpdateDto) {
        paymentService.update(paymentUpdateDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
