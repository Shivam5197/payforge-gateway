package com.payforge.gateway.paymentevent.service.impl;

import com.payforge.gateway.common.events.DomainEventPublisher;
import com.payforge.gateway.payment.dto.PaymentStatus;
import com.payforge.gateway.payment.entity.Payment;
import com.payforge.gateway.payment.exception.PaymentNotFoundException;
import com.payforge.gateway.payment.repository.PaymentRepository;
import com.payforge.gateway.paymentevent.dto.PaymentEventResponseDTO;
import com.payforge.gateway.paymentevent.dto.PaymentEventType;
import com.payforge.gateway.paymentevent.entity.PaymentEvent;
import com.payforge.gateway.paymentevent.repository.PaymentEventRepository;
import com.payforge.gateway.paymentevent.service.PaymentEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentEventServiceImpl implements PaymentEventService {

    private final DomainEventPublisher publisher;
    private final PaymentEventRepository paymentEventRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public void publishPaymentCreated(Payment payment) {

        PaymentEvent event = PaymentEvent.builder()
                        .id(UUID.randomUUID())
                        .payment(payment)
                        .eventType(
                                PaymentEventType.PAYMENT_CREATED)
                        .newStatus(
                                PaymentStatus.CREATED)
                        .actor("MERCHANT")
                        .source("PAYMENT_SERVICE")
                        .remarks("Payment created")
                        .build();

        publisher.publish(event);
    }
    /**
     * @param payment
     */
    @Override
    public void publishPaymentProcessing(Payment payment) {
        PaymentEvent event = PaymentEvent.builder()
                .id(UUID.randomUUID())
                .payment(payment)
                .eventType(PaymentEventType.PAYMENT_PROCESSING)
                .oldStatus(PaymentStatus.CREATED)
                .newStatus(PaymentStatus.PROCESSING)
                .actor("SYSTEM")
                .source("PAYMENT_SERVICE")
                .remarks("Payment processing started")
                .build();

        publisher.publish(event);
    }

    /**
     * @param payment
     */
    @Override
    public void publishPaymentSucceeded(Payment payment) {
        PaymentEvent event = PaymentEvent.builder()
                .id(UUID.randomUUID())
                .payment(payment)
                .eventType(PaymentEventType.PAYMENT_SUCCEEDED)
                .oldStatus(PaymentStatus.PROCESSING)
                .newStatus(PaymentStatus.SUCCESS)
                .actor("SYSTEM")
                .source("PAYMENT_SERVICE")
                .remarks("Payment completed successfully")
                .build();

        publisher.publish(event);
    }

    /**
     * @param payment
     */
    @Override
    public void publishPaymentFailed(Payment payment) {
        PaymentEvent event = PaymentEvent.builder()
                .id(UUID.randomUUID())
                .payment(payment)
                .eventType(PaymentEventType.PAYMENT_FAILED)
                .oldStatus(PaymentStatus.PROCESSING)
                .newStatus(PaymentStatus.FAILED)
                .actor("SYSTEM")
                .source("PAYMENT_SERVICE")
                .remarks("Payment processing failed")
                .build();

        publisher.publish(event);
    }

    /**
     * @param paymentReference
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<PaymentEventResponseDTO> getPaymentTimeline(String paymentReference) {

        Payment payment = paymentRepository.findByPaymentReference(paymentReference).orElseThrow(
                () -> new PaymentNotFoundException("PAYMENT_NOT_FOUND")
        );

        return paymentEventRepository.findByPaymentIdOrderByCreatedAtAsc(payment.getId())
                .stream()
                .map(event -> PaymentEventResponseDTO.builder()
                        .eventType(event.getEventType().name())
                        .oldStatus(event.getOldStatus() == null
                                ? null
                                : event.getOldStatus().name())
                        .newStatus(event.getNewStatus().name())
                        .actor(event.getActor())
                        .source(event.getSource())
                        .remarks(event.getRemarks())
                        .createdAt(event.getCreatedAt())
                        .build())
                .toList();
    }
}
