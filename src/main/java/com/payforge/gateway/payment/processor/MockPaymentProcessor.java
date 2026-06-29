package com.payforge.gateway.payment.processor;

import com.payforge.gateway.payment.dto.PaymentStatus;
import com.payforge.gateway.payment.entity.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
public class MockPaymentProcessor implements PaymentProcessor {

    @Value("${payment.processor.mock.success-limit}")
     private long successLimit;

    @Override
    public PaymentProcessingResult process(Payment payment) {

        System.out.print(successLimit);

        if (payment.getAmount() <= successLimit) {

            return PaymentProcessingResult.builder()
                    .status(PaymentStatus.SUCCESS)
                    .responseCode("00")
                    .responseMessage("Approved")
                    .build();
        }

        return PaymentProcessingResult.builder()
                .status(PaymentStatus.FAILED)
                .responseCode("05")
                .responseMessage("Do Not Honor")
                .build();
    }
}