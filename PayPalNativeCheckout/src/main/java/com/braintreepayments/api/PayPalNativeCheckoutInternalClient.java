package com.braintreepayments.api;

import androidx.annotation.VisibleForTesting;

public class PayPalNativeCheckoutInternalClient {

    private static final String CREATE_SINGLE_PAYMENT_ENDPOINT = "paypal_hermes/create_payment_resource";
    private static final String SETUP_BILLING_AGREEMENT_ENDPOINT = "paypal_hermes/setup_billing_agreement";

    private final BraintreeClient braintreeClient;
    private final PayPalDataCollector payPalDataCollector;
    private final ApiClient apiClient;

    PayPalNativeCheckoutInternalClient(BraintreeClient braintreeClient) {
        this(braintreeClient, new PayPalDataCollector(), new ApiClient(braintreeClient));
    }

    @VisibleForTesting
    PayPalNativeCheckoutInternalClient(BraintreeClient braintreeClient, PayPalDataCollector payPalDataCollector, ApiClient apiClient) {
        this.braintreeClient = braintreeClient;
        this.payPalDataCollector = payPalDataCollector;
        this.apiClient = apiClient;
    }

    // make request to create BA-ID or EC-token here

    public void makeRequest() {
        braintreeClient.getAuthorization((authorization, authError) -> {
            if (authorization != null) {
                braintreeClient.getConfiguration((configuration, configError) -> {
                    // handle error


                    boolean isBillingAgreement = true // need to handle BA case
                    String endpoint = isBillingAgreement
                            ? SETUP_BILLING_AGREEMENT_ENDPOINT : CREATE_SINGLE_PAYMENT_ENDPOINT;
                    String url = String.format("/v1/%s", endpoint);
                });
            }
        });
    }
}
