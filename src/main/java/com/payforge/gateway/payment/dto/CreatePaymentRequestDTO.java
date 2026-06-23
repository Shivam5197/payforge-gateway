package com.payforge.gateway.payment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
public class CreatePaymentRequestDTO {

    @NotNull
    @Positive
    private Long amount;

    @NotNull
    private Currency currency;

    @NotBlank
    private String idempotencyKey;

    private String description;
}