package com.payforge.gateway.common.events;

import com.payforge.gateway.paymentevent.entity.PaymentEvent;

public interface DomainEventPublisher {

    void publish(
            PaymentEvent event);
}