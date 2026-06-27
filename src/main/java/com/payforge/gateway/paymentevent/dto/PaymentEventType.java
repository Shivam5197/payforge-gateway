package com.payforge.gateway.paymentevent.dto;

public enum PaymentEventType {

    PAYMENT_CREATED,

    PAYMENT_PROCESSING,

    PAYMENT_SUCCEEDED,

    PAYMENT_FAILED,

    REFUND_CREATED,

    REFUND_SUCCEEDED,

    REFUND_FAILED
}