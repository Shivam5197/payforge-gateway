package com.payforge.gateway.common.utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public final class PaymentReferenceGenerator {

    private PaymentReferenceGenerator() {
    }

    public static String generate() {

        String date = LocalDate.now().format(DateTimeFormatter.ISO_DATE);

        String random =
                UUID.randomUUID()
                        .toString()
                        .replace("-", "")
                        .substring(0, 8)
                        .toUpperCase();

        return "PAY_"+ date +"_"+ random;
    }
}