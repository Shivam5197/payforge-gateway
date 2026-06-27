package com.payforge.gateway.paymentevent.repository;

import com.payforge.gateway.paymentevent.entity.PaymentEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PaymentEventRepository extends JpaRepository<PaymentEvent, UUID> {
    List<PaymentEvent> findByPaymentIdOrderByCreatedAtAsc(
            UUID paymentId);
}