package com.matheus.commerce.domain;


import com.matheus.commerce.dto.billingDetails.BillingDetailsDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "billing_details")
@Table(name = "billing_details")
public class BillingDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    @Column(name = "last_name")
    private String lastName;
    private String email;
    private String phone;
    private String cep;
    private String city;
    private String street;
    private String neighborhood;
    @OneToOne(mappedBy = "billingDetails")
    private Order order;

    public BillingDetails(BillingDetailsDto billingDetailsDto){
        this.name=billingDetailsDto.name();
        this.lastName= billingDetailsDto.lastName();
        this.email= billingDetailsDto.email();
        this.phone= billingDetailsDto.phone();
        this.cep=billingDetailsDto.cep();
        this.city= billingDetailsDto.city();
        this.street=billingDetailsDto.street();
        this.neighborhood= billingDetailsDto.neighborhood();

    }
}
