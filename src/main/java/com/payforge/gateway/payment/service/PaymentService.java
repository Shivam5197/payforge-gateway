package com.payforge.gateway.payment.service;

import com.payforge.gateway.payment.dto.CreatePaymentRequestDTO;
import com.payforge.gateway.payment.dto.PaymentResponseDTO;

public interface PaymentService {

    PaymentResponseDTO createPayment(CreatePaymentRequestDTO createPaymentRequestDTO);

}
