package com.payforge.gateway.paymentevent.controller;

import com.payforge.gateway.paymentevent.dto.PaymentEventResponseDTO;
import com.payforge.gateway.paymentevent.service.PaymentEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentEventController {

    private final PaymentEventService paymentEventService;

    @GetMapping("/{paymentReference}/events")
    public List<PaymentEventResponseDTO> getTimeline(
            @PathVariable String paymentReference) {

        return paymentEventService
                .getPaymentTimeline(paymentReference);
    }
}