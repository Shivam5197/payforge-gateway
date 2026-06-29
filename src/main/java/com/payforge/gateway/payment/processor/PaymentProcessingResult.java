package com.payforge.gateway.payment.processor;

import com.payforge.gateway.payment.dto.PaymentStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentProcessingResult {

    private PaymentStatus status;

    private String responseCode;

    private String responseMessage;
}