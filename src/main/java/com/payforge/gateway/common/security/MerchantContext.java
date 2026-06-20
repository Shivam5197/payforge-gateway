package com.payforge.gateway.common.security;

import com.payforge.gateway.merchant.entity.Merchant;

public final class MerchantContext {

    private static final ThreadLocal<Merchant>
            CURRENT_MERCHANT = new ThreadLocal<>();

    private MerchantContext() {
    }

    public static void set(
            Merchant merchant) {

        CURRENT_MERCHANT.set(merchant);
    }

    public static Merchant get() {

        return CURRENT_MERCHANT.get();
    }

    public static void clear() {

        CURRENT_MERCHANT.remove();
    }
}