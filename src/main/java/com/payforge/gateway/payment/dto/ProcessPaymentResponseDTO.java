package com.payforge.gateway.payment.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class ProcessPaymentResponseDTO {

    private UUID paymentId;

    private String paymentReference;

    private String status;

    private String gatewayResponseCode;

    private String gatewayMessage;
}