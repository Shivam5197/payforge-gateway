package com.payforge.gateway.payment.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Getter
@Setter
public class PaymentStatusResponseDTO {

    private UUID paymentId;
    private String paymentReference;
    private String status;
    private BigDecimal amount;
    private String currency;
    private String description;
}