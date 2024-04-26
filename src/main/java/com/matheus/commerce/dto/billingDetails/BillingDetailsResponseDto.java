package com.matheus.commerce.dto.billingDetails;

import com.matheus.commerce.domain.BillingDetails;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillingDetailsResponseDto {
    private String id;
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private String cep;
    private String city;
    private String street;
    private String neighborhood;

    public BillingDetailsResponseDto(BillingDetails billingDetails){
        this.id=billingDetails.getId();
        this.name=billingDetails.getName();
        this.lastName= billingDetails.getLastName();
        this.email= billingDetails.getEmail();
        this.phone= billingDetails.getPhone();
        this.cep=billingDetails.getCep();
        this.city= billingDetails.getCity();
        this.street=billingDetails.getStreet();
        this.neighborhood= billingDetails.getNeighborhood();
    }
}
