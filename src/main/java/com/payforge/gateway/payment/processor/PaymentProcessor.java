package com.payforge.gateway.payment.processor;

import com.payforge.gateway.payment.entity.Payment;

public interface PaymentProcessor {

    PaymentProcessingResult process(Payment payment);

}