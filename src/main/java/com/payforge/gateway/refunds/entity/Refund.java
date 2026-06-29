package com.payforge.gateway.refunds.entity;

import com.payforge.gateway.payment.entity.Payment;
import com.payforge.gateway.refunds.dto.RefundStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "refund")
public class Refund {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    String refundReference;

    @ManyToOne
    Payment payment;

    BigDecimal amount;

    String reason;

    RefundStatus status;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;
}