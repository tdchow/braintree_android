package com.braintreepayments.api;

import androidx.annotation.NonNull;

import org.json.JSONException;

public class PayPalNativeCheckoutRequest extends PayPalNativeRequest {

    private final String ecToken;
    private final String returnUrl;
    private final String correlationId;

    public PayPalNativeCheckoutRequest(@NonNull String ecToken, @NonNull String returnUrl, @NonNull String correlationId) {
        this.ecToken = ecToken;
        this.returnUrl = returnUrl;
        this.correlationId = correlationId;
    }

    @NonNull
    public String getEcToken() {
        return ecToken;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    @Override
    String createRequestBody(Configuration configuration, Authorization authorization, String successUrl, String cancelUrl) throws JSONException {

    }
}
