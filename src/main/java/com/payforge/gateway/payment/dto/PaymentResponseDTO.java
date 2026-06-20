package com.payforge.gateway.payment.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Builder
public class PaymentResponseDTO {

    private UUID paymentId;

    private String paymentReference;

    private Long amount;

    private String currency;

    private String status;
}