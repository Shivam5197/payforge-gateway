package com.payforge.gateway.payment.service.impl;

import com.payforge.gateway.apikey.exception.InvalidAPIKeyException;
import com.payforge.gateway.common.security.MerchantContext;
import com.payforge.gateway.common.utility.PaymentReferenceGenerator;
import com.payforge.gateway.merchant.entity.Merchant;
import com.payforge.gateway.payment.dto.*;
import com.payforge.gateway.payment.entity.Payment;
import com.payforge.gateway.payment.exception.InvalidPaymentException;
import com.payforge.gateway.payment.exception.PaymentNotFoundException;
import com.payforge.gateway.payment.processor.PaymentProcessingResult;
import com.payforge.gateway.payment.processor.PaymentProcessor;
import com.payforge.gateway.payment.repository.PaymentRepository;
import com.payforge.gateway.payment.service.PaymentService;
import com.payforge.gateway.paymentevent.service.PaymentEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentProcessor paymentProcessor;
    private final PaymentEventService paymentEventService;

    @Override
    public PaymentResponseDTO createPayment(CreatePaymentRequestDTO request) {

        Merchant merchant = MerchantContext.get();

        if (merchant == null) {
            throw new InvalidAPIKeyException(
                    "Merchant context missing");
        }

        if (request.getAmount() < 100) {
            throw new InvalidPaymentException("Minimum amount is 100");
        }

        if (request.getAmount() > 10_000_000) {
            throw new InvalidPaymentException("Maximum amount exceeded");
        }

        Optional<Payment> existingPayment = paymentRepository.findByIdempotencyKey(request.getIdempotencyKey());

        if (existingPayment.isPresent()) {
            Payment payment = existingPayment.get();

            return mapToResponse(payment);
        }

        Payment payment =
                Payment.builder()
                        .id(UUID.randomUUID())
                        .merchant(merchant)
                        .paymentReference(PaymentReferenceGenerator.generate())
                        .amount(request.getAmount())
     //                   .idempotencyKey(request.getIdempotencyKey())
                        .idempotencyKey(idempotencyKeyGenerator())
                        .currency(request.getCurrency())
                        .description(request.getDescription())
                        .status(PaymentStatus.CREATED)
                        .build();

        Payment saved =
                paymentRepository.save(payment);

        paymentEventService
                .publishPaymentCreated(saved);

        return mapToResponse(payment);
    }

    /**
     * @param uuid
     * @return
     */
    @Override
    public PaymentResponseDTO getPaymentById(UUID uuid) {

        Merchant merchant = MerchantContext.get();

        Payment payment = paymentRepository.findById(uuid)
                .orElseThrow(() ->
                        new PaymentNotFoundException("Payment Not Found"));
        if (!payment.getMerchant().getId().equals(merchant.getId())) {
            throw new PaymentNotFoundException("PAYMENT_NOT_FOUND");
        }
        return mapToResponse(payment);
    }

    /**
     * @param paymentReference
     * @return
     */
    @Override
    public PaymentResponseDTO findByPaymentReference(String paymentReference) {

        Merchant merchant = MerchantContext.get();

        Payment payment = paymentRepository.findByPaymentReference(paymentReference)
                .orElseThrow(() ->
                        new PaymentNotFoundException("Payment Not Found"));
        if (!payment.getMerchant().getId().equals(merchant.getId())) {
            throw new PaymentNotFoundException("PAYMENT_NOT_FOUND");
        }
        return mapToResponse(payment);
    }

    /**
     * @param paymentReference
     * @return
     */
    @Override
    public PaymentStatusResponseDTO getPaymentStatus(String paymentReference) {
        Merchant merchant = MerchantContext.get();

        Payment payment = paymentRepository.findByPaymentReference(paymentReference)
                .orElseThrow(() ->
                        new PaymentNotFoundException("Payment_Not_Found"));
        if (!payment.getMerchant().getId().equals(merchant.getId())) {
            throw new PaymentNotFoundException("PAYMENT_NOT_FOUND");
        }
        return PaymentStatusResponseDTO.builder()
                .paymentId(payment.getId())
                .paymentReference(payment.getPaymentReference())
                .status(payment.getStatus().name())
                .amount(BigDecimal.valueOf(payment.getAmount()))
                .currency(payment.getCurrency().name())
                .description(payment.getDescription())
                .build();
    }

    private PaymentResponseDTO mapToResponse(
            Payment payment) {
        return PaymentResponseDTO.builder()
                .paymentId(payment.getId())
                .paymentReference(payment.getPaymentReference())
                .amount(payment.getAmount())
                .idempotencyKey(payment.getIdempotencyKey())
                .currency(Currency.valueOf(payment.getCurrency().name()))
                .status(payment.getStatus().name())
                .build();
    }


    @Override
    @Transactional
    public ProcessPaymentResponseDTO processPayment(UUID paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found"));

        // Prevent re-processing
        if (payment.getStatus() == PaymentStatus.SUCCESS
                || payment.getStatus() == PaymentStatus.FAILED) {
            throw new InvalidPaymentException("Payment already processed");
        }

        PaymentStateValidator.validatePaymentStatus(payment);
        payment.setStatus(PaymentStatus.PROCESSING);
        paymentRepository.save(payment);

        paymentEventService.publishPaymentProcessing(payment);

        // SIMULATE PAYMENT GATEWAY
        PaymentProcessingResult result = paymentProcessor.process(payment);

        String responseCode = result.getResponseCode();
        String responseMessage = result.getResponseMessage();

        if (result.getStatus() == PaymentStatus.SUCCESS) {
            payment.setStatus(PaymentStatus.SUCCESS);
            paymentRepository.save(payment);
            paymentEventService.publishPaymentSucceeded(payment);
            responseCode = "00";
            responseMessage = "Approved";
        } else {
            payment.setStatus(PaymentStatus.FAILED);
            paymentRepository.save(payment);
            paymentEventService.publishPaymentFailed(payment);
            responseCode = "05";
            responseMessage = "Do Not Honor";
        }
        return ProcessPaymentResponseDTO.builder()
                .paymentId(payment.getId())
                .paymentReference(payment.getPaymentReference())
                .status(payment.getStatus().name())
                .gatewayResponseCode(responseCode)
                .gatewayMessage(responseMessage)
                .build();
    }

    private String idempotencyKeyGenerator() {
        return "Order-" + ThreadLocalRandom.current().nextInt(100, 1000);
    }

}