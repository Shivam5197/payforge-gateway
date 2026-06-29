package com.payforge.gateway.paymentevent.service;

import com.payforge.gateway.payment.entity.Payment;
import com.payforge.gateway.paymentevent.dto.PaymentEventResponseDTO;

import java.util.List;

public interface PaymentEventService {

    void publishPaymentCreated(Payment payment);

    void publishPaymentProcessing(Payment payment);

    void publishPaymentSucceeded(Payment payment);

    void publishPaymentFailed(Payment payment);

    List<PaymentEventResponseDTO> getPaymentTimeline(String paymentReference);
}