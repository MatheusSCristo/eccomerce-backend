package com.matheus.commerce.domain;

import com.matheus.commerce.dto.PaymentDto;
import com.matheus.commerce.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment")
@Entity(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @OneToOne(mappedBy = "payment")
    private Order order;
    @Column(name = "payment_status", length = 255)
    private PaymentStatus paymentStatus=PaymentStatus.pending;
    public Payment(Order order){
        this.order=order;
    }

    public Payment(PaymentDto paymentDto) {

    }
}
