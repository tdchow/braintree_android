package com.braintreepayments.api;

import org.json.JSONException;
import org.json.JSONObject;

class VenmoAccount extends PaymentMethod {

    private static final String VENMO_ACCOUNT_KEY = "venmoAccount";
    private static final String NONCE_KEY = "nonce";

    private String nonce;

    VenmoAccount() {}

    void setNonce(String nonce) {
        this.nonce = nonce;
    }

    @Override
    protected void buildJSON(JSONObject base, JSONObject paymentMethodNonceJson) throws JSONException {
        paymentMethodNonceJson.put(NONCE_KEY, nonce);
        base.put(VENMO_ACCOUNT_KEY, paymentMethodNonceJson);
    }

    @Override
    protected void buildGraphQL(JSONObject base, JSONObject input) {}

    @Override
    public String getApiPath() {
        return "venmo_accounts";
    }

    @Override
    public String getResponsePaymentMethodType() {
        return VenmoAccountNonce.TYPE;
    }
}