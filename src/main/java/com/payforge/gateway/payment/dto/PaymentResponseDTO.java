package com.payforge.gateway.payment.dto;

import com.payforge.gateway.payment.entity.Payment;
import lombok.*;

import java.util.UUID;

@Getter
@Builder
public class PaymentResponseDTO {

    private UUID paymentId;

    private String paymentReference;

    private Long amount;

    private Currency currency;

    private String status;
    private String idempotencyKey;

}