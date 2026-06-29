package com.payforge.gateway.payment.service;

import com.payforge.gateway.payment.dto.CreatePaymentRequestDTO;
import com.payforge.gateway.payment.dto.PaymentResponseDTO;
import com.payforge.gateway.payment.dto.PaymentStatusResponseDTO;
import com.payforge.gateway.payment.dto.ProcessPaymentResponseDTO;

import java.util.UUID;

public interface PaymentService {

    PaymentResponseDTO createPayment(CreatePaymentRequestDTO createPaymentRequestDTO);

    PaymentResponseDTO getPaymentById(UUID uuid);

    PaymentResponseDTO findByPaymentReference(String paymentReference);

    ProcessPaymentResponseDTO processPayment(
            UUID paymentId);

    PaymentStatusResponseDTO getPaymentStatus(String paymentReference);

}
