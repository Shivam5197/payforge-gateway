package com.payforge.gateway.payment.exception;

public class PaymentNotFoundException  extends RuntimeException{

    public PaymentNotFoundException (String message){
        super(message);
    }
}
