package com.payforge.gateway.paymentevent.entity;

import com.payforge.gateway.payment.dto.PaymentStatus;
import com.payforge.gateway.payment.entity.Payment;
import com.payforge.gateway.paymentevent.dto.PaymentEventType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payment_events")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEvent {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentEventType eventType;

    @Enumerated(EnumType.STRING)
    private PaymentStatus oldStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus newStatus;

    @Column(length = 500)
    private String remarks;

    @Column(nullable = false)
    private String actor;

    @Column(nullable = false)
    private String source;

    @CreationTimestamp
    private LocalDateTime createdAt;
}