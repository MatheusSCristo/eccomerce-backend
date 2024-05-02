package com.matheus.commerce.dto.AdminResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminResponse {
    private boolean authorized;
    private String userEmail;
}
