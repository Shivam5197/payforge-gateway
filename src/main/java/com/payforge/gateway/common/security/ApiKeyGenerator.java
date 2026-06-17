package com.payforge.gateway.common.security;

import java.security.SecureRandom;
import java.util.Base64;

public final class ApiKeyGenerator {

    private static final SecureRandom RANDOM =
            new SecureRandom();

    private ApiKeyGenerator() {
    }

    public static String generate() {

        byte[] bytes = new byte[32];

        RANDOM.nextBytes(bytes);

        return "pk_live_" +
                Base64.getUrlEncoder()
                        .withoutPadding()
                        .encodeToString(bytes);
    }
}