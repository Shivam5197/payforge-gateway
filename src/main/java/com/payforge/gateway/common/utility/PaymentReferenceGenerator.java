package com.payforge.gateway.common.utility;

import java.util.UUID;

public final class PaymentReferenceGenerator {

    private PaymentReferenceGenerator() {
    }

    public static String generate() {

        String random =
                UUID.randomUUID()
                        .toString()
                        .replace("-", "")
                        .substring(0, 8)
                        .toUpperCase();

        return "PAY_" + random;
    }
}