package com.payforge.gateway.payment.controller;

import com.payforge.gateway.payment.dto.CreatePaymentRequestDTO;
import com.payforge.gateway.payment.dto.PaymentResponseDTO;
import com.payforge.gateway.payment.dto.PaymentStatusResponseDTO;
import com.payforge.gateway.payment.dto.ProcessPaymentResponseDTO;
import com.payforge.gateway.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


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

    @GetMapping("/{paymentId}/id")
    public PaymentResponseDTO
    getPayment(
            @PathVariable UUID paymentId) {

        return paymentService
                .getPaymentById(paymentId);
    }

    @GetMapping("/{paymentReference}")
    public PaymentResponseDTO
    getPayment(@PathVariable String paymentReference) {
        return paymentService
                .findByPaymentReference(paymentReference);
    }

    @GetMapping("/status/{paymentReference}")
    public PaymentStatusResponseDTO getPaymentStatus(
            @PathVariable String paymentReference) {

        return paymentService
                .getPaymentStatus(paymentReference);
    }

    @PostMapping("/{paymentId}/process")
    public ProcessPaymentResponseDTO
    processPayment(
            @PathVariable UUID paymentId) {

        return paymentService
                .processPayment(paymentId);
    }
}