package com.matheus.commerce.infra.exceptionsHandlers;

import com.matheus.commerce.infra.exceptions.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestHandlerException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<ExceptionResponse> UserNotFoundErrorHandler(UserNotFoundException exception) {
        ExceptionResponse responseExceptions = new ExceptionResponse("User with the data provided does not exists.", HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(responseExceptions.getError()).body(responseExceptions);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    private ResponseEntity<ExceptionResponse> ProductNotFoundErrorHandler(ProductNotFoundException exception) {
        ExceptionResponse responseExceptions = new ExceptionResponse("Product with the data provided does not exists.", HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(responseExceptions.getError()).body(responseExceptions);
    }

    @ExceptionHandler(PaymentStillProcessingException.class)
    private ResponseEntity<ExceptionResponse> PaymentProcessingErrorHandler(PaymentStillProcessingException exception) {
        ExceptionResponse responseExceptions = new ExceptionResponse("Can`t create other payment while there is already one in process", HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(responseExceptions.getError()).body(responseExceptions);
    }

    @ExceptionHandler(PaymentNotFoundException.class)
    private ResponseEntity<ExceptionResponse> PaymentNotFoundErrorHandler(PaymentNotFoundException exception) {
        ExceptionResponse responseExceptions = new ExceptionResponse("Payment process with the data provided does not exists.", HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(responseExceptions.getError()).body(responseExceptions);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    private ResponseEntity<ExceptionResponse> OrderNotFoundErrorHandler(OrderNotFoundException exception) {
        ExceptionResponse responseExceptions = new ExceptionResponse("Order with the data provided does not exists.", HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(responseExceptions.getError()).body(responseExceptions);
    }

    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    private ResponseEntity<ExceptionResponse> EmailAlreadyRegisteredErrorHandler(EmailAlreadyRegisteredException exception) {
        ExceptionResponse responseExceptions = new ExceptionResponse("Email already registered in the database", HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(responseExceptions.getError()).body(responseExceptions);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<ExceptionResponse> EmailAlreadyRegisteredErrorHandler(IllegalArgumentException exception) {
        ExceptionResponse responseExceptions = new ExceptionResponse("Data invalid", HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(responseExceptions.getError()).body(responseExceptions);
    }

    @ExceptionHandler(TokenNotValidException.class)
    private ResponseEntity<ExceptionResponse> TokenInvalidErrorHandler(TokenNotValidException exception) {
        ExceptionResponse responseExceptions = new ExceptionResponse("Token is invalid", HttpStatus.UNAUTHORIZED);
        return ResponseEntity.status(responseExceptions.getError()).body(responseExceptions);
    }

    @ExceptionHandler(AuthenticationException.class)
    private ResponseEntity<ExceptionResponse> AuthenticationErrorHandler(AuthenticationException exception) {
        ExceptionResponse responseExceptions = new ExceptionResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(responseExceptions.getError()).body(responseExceptions);
    }

    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity<ExceptionResponse> InternalServerError(RuntimeException exception) {
        ExceptionResponse responseExceptions = new ExceptionResponse("Internal error", HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(responseExceptions.getError()).body(responseExceptions);
    }


    @ExceptionHandler(Exception.class)
    private ResponseEntity<ExceptionResponse> InternalServerError(Exception exception) {
        ExceptionResponse responseExceptions = new ExceptionResponse("Internal error", HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(responseExceptions.getError()).body(responseExceptions);
    }


}
