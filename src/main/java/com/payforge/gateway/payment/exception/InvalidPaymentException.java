package com.payforge.gateway.payment.exception;

public class InvalidPaymentException extends RuntimeException{

    public InvalidPaymentException(String message){
        super(message);
    }
}
