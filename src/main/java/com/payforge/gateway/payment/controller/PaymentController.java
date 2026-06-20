package com.payforge.gateway.payment.controller;

import com.payforge.gateway.payment.dto.CreatePaymentRequestDTO;
import com.payforge.gateway.payment.dto.PaymentResponseDTO;
import com.payforge.gateway.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResponseDTO createPayment(
            @Valid
            @RequestBody
            CreatePaymentRequestDTO request) {

        return paymentService
                .createPayment(request);
    }

}