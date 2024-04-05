package com.matheus.commerce.controller;

import com.matheus.commerce.domain.Payment;
import com.matheus.commerce.dto.PaymentDto;
import com.matheus.commerce.dto.PaymentResponseDto;
import com.matheus.commerce.dto.PaymentUpdateDto;
import com.matheus.commerce.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @GetMapping
    public ResponseEntity<List<PaymentResponseDto>> findAll(){
        List<Payment> payments=paymentService.findAll();
        List<PaymentResponseDto> paymentResponseDtoList=new ArrayList<>();
        for(Payment payment:payments){
            paymentResponseDtoList.add(new PaymentResponseDto(payment));
        }
        return ResponseEntity.ok().body(paymentResponseDtoList);
    }
    @PutMapping
    public ResponseEntity<Void> update(@RequestBody PaymentUpdateDto paymentUpdateDto){
        paymentService.update(paymentUpdateDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
