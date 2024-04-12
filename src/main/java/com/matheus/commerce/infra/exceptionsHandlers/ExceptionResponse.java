package com.matheus.commerce.infra.exceptionsHandlers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {
    private LocalDate timestamp = LocalDate.now();
    private String message;
    private HttpStatus error;

    public ExceptionResponse(String message,HttpStatus error){
        this.message=message;
        this.error=error;
    }
}
