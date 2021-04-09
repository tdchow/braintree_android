package com.braintreepayments.api;

import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;

import com.visa.checkout.Environment;

import java.util.List;

/**
 * Used to create and tokenize Visa Checkout. For more information see the
 * <a href="https://developers.braintreepayments.com/guides/visa-checkout/overview">documentation</a>
 */
public class VisaCheckoutClient {

    private final BraintreeClient braintreeClient;
    private final TokenizationClient tokenizationClient;

    public VisaCheckoutClient(BraintreeClient braintreeClient) {
        this(braintreeClient, new TokenizationClient(braintreeClient));
    }

    @VisibleForTesting
    VisaCheckoutClient(BraintreeClient braintreeClient, TokenizationClient tokenizationClient) {
        this.braintreeClient = braintreeClient;
        this.tokenizationClient = tokenizationClient;
    }

    /**
     * Creates a {@link VisaCheckoutProfile} with the merchant API key, environment, and other properties to be used with
     * Visa Checkout.
     *
     * @param callback {@link VisaCheckoutCreateProfileBuilderCallback}
     */
    public void createProfileBuilder(final VisaCheckoutCreateProfileBuilderCallback callback) {
        braintreeClient.getConfiguration(new ConfigurationCallback() {
            @Override
            public void onResult(@Nullable Configuration configuration, @Nullable Exception e) {
                if (!configuration.isVisaCheckoutEnabled()) {
                    callback.onResult(null, new ConfigurationException("Visa Checkout is not enabled."));
                    return;
                }

                String merchantApiKey = configuration.getVisaCheckoutApiKey();
                List<String> acceptedCardBrands = configuration.getVisaCheckoutSupportedNetworks();

                String environment = "sandbox";
                if ("production".equals(configuration.getEnvironment())) {
                    environment = "production";
                }
                VisaCheckoutProfile profile = new VisaCheckoutProfile(merchantApiKey, environment, acceptedCardBrands, configuration.getVisaCheckoutExternalClientId());

                callback.onResult(profile, null);
            }
        });
    }
//
//    static boolean isVisaCheckoutSDKAvailable() {
//        try {
//            Class.forName("com.visa.checkout.VisaCheckoutSdk");
//            return true;
//        } catch (ClassNotFoundException e) {
//            return false;
//        }
//    }

    /**
     * Tokenizes the payment summary of the Visa Checkout flow.
     *
     * @param visaPaymentSummary {@link VisaCheckoutPaymentSummary} The Visa payment to tokenize.
     * @param callback {@link VisaCheckoutTokenizeCallback}
     */
    public void tokenize(VisaCheckoutPaymentSummary visaPaymentSummary, final VisaCheckoutTokenizeCallback callback) {
        tokenizationClient.tokenizeREST(new VisaCheckoutAccount(visaPaymentSummary), new TokenizeCallback() {
            @Override
            public void onResult(JSONObject tokenizationResponse, Exception exception) {
                if (tokenizationResponse != null) {
                    try {
                        VisaCheckoutNonce visaCheckoutNonce = VisaCheckoutNonce.fromJSON(tokenizationResponse);
                        callback.onResult(visaCheckoutNonce, null);
                        braintreeClient.sendAnalyticsEvent("visacheckout.tokenize.succeeded");
                    } catch (JSONException e) {
                        callback.onResult(null, e);
                    }
                } else {
                    callback.onResult(null, exception);
                    braintreeClient.sendAnalyticsEvent("visacheckout.tokenize.failed");
                }
            }
        });
    }
}
