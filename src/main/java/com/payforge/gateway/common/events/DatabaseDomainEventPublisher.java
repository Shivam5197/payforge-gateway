package com.payforge.gateway.common.events;

import com.payforge.gateway.paymentevent.entity.PaymentEvent;
import com.payforge.gateway.paymentevent.repository.PaymentEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DatabaseDomainEventPublisher
        implements DomainEventPublisher {

    private final PaymentEventRepository paymentEventRepository;

    @Override
    public void publish(PaymentEvent event) {
        paymentEventRepository.save(event);
    }
}