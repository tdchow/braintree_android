package com.braintreepayments.demo;

import android.content.Context;

import com.braintreepayments.api.BraintreeClient;

public class BraintreeClientFactory {

    private BraintreeClientFactory() {}

    public static BraintreeClient createBraintreeClient(Context context) {
        if (Settings.useTokenizationKey(context)) {
            String tokenizationKey = Settings.getTokenizationKey(context);
            return new BraintreeClient(context, tokenizationKey);
        } else {
            return new BraintreeClient(context, new DemoAuthorizationProvider(context));
        }
    }
}
