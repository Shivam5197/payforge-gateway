package com.payforge.gateway.paymentevent.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PaymentEventResponseDTO {

    private String eventType;

    private String oldStatus;

    private String newStatus;

    private String actor;

    private String source;

    private String remarks;

    private LocalDateTime createdAt;
}
