package com.payforge.gateway.payment.service.impl;

import com.payforge.gateway.payment.dto.PaymentStatus;
import com.payforge.gateway.payment.entity.Payment;
import com.payforge.gateway.payment.exception.InvalidPaymentException;

public class PaymentStateValidator {

    public static void validatePaymentStatus(Payment payment){

        if(payment.getStatus() != PaymentStatus.CREATED){
            throw  new InvalidPaymentException("Payment cannot be processed from state "
                    + payment.getStatus());
        }
    }
}
