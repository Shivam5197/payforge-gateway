package com.payforge.gateway.payment.service.impl;

import com.payforge.gateway.apikey.exception.InvalidAPIKeyException;
import com.payforge.gateway.common.security.MerchantContext;
import com.payforge.gateway.common.utility.PaymentReferenceGenerator;
import com.payforge.gateway.merchant.entity.Merchant;
import com.payforge.gateway.payment.dto.CreatePaymentRequestDTO;
import com.payforge.gateway.payment.dto.PaymentResponseDTO;
import com.payforge.gateway.payment.dto.PaymentStatus;
import com.payforge.gateway.payment.entity.Payment;
import com.payforge.gateway.payment.repository.PaymentRepository;
import com.payforge.gateway.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl
        implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public PaymentResponseDTO createPayment(
            CreatePaymentRequestDTO request) {

        Merchant merchant =
                MerchantContext.get();

        if (merchant == null) {
            throw new InvalidAPIKeyException("Merchant context missing");
        }

        Payment payment =
                Payment.builder().id(UUID.randomUUID()).merchant(merchant)
                        .paymentReference(PaymentReferenceGenerator.generate())
                        .amount(request.getAmount())
                        .currency(request.getCurrency())
                        .description(request.getDescription())
                        .status(PaymentStatus.CREATED)
                        .build();

        Payment saved = paymentRepository.save(payment);

        return PaymentResponseDTO.builder().paymentId(saved.getId())
                .paymentReference(saved.getPaymentReference())
                .amount(saved.getAmount())
                .currency(saved.getCurrency())
                .status(saved.getStatus().name())
                .build();
    }
}