package com.braintreepayments.api;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.paypal.checkout.PayPalCheckout;
import com.paypal.checkout.config.CheckoutConfig;
import com.paypal.checkout.config.Environment;

public class PayPalNativeCheckoutClient {

    private PayPalNativeCheckoutListener listener;
    private final BraintreeClient braintreeClient;

    public PayPalNativeCheckoutClient(BraintreeClient braintreeClient) {
        this.braintreeClient = braintreeClient;
    }

//    public void tokenize(PayPalNativeCheckoutRequest request) {
//        // TODO: start native checkout
//    }

     public void setListener(PayPalNativeCheckoutListener listener) {
        this.listener = listener;
     }

    /**
     * Tokenize a PayPal account for vault or checkout.
     * <p>
     * This method must be invoked on a {@link PayPalNativeCheckoutClient (Fragment, BraintreeClient)} or
     * {@link PayPalNativeCheckoutClient (FragmentActivity, BraintreeClient)} in order to receive results.
     *
     * @param activity      Android FragmentActivity
     * @param payPalRequest a {@link PayPalNativeRequest} used to customize the request.
     */
    public void tokenizePayPalAccount(
        @NonNull final FragmentActivity activity,
        @NonNull final PayPalNativeRequest payPalRequest
    ) {
        braintreeClient.getConfiguration((configuration, error) -> {

//            if (payPalConfigInvalid(configuration)) {
//                Exception configInvalidError = createPayPalError();
//                callback.onResult(configInvalidError);
//                return;
//            }

            sendPayPalRequest(
                activity,
                payPalRequest,
                configuration
            );
        });
    }

     private void sendPayPalRequest(
        final FragmentActivity activity,
        final PayPalNativeCheckoutRequest payPalRequest,
        final Configuration configuration
    ) {
         Environment environment;
         if ("sandbox".equals(configuration.getEnvironment())) {
             environment = Environment.SANDBOX;
         } else {
             environment = Environment.LIVE;
         }
         // Start PayPalCheckout flow
         PayPalCheckout.setConfig(
             new CheckoutConfig(
                     activity.getApplication(),
                     configuration.getPayPalClientId(),
                     environment,
                     payPalRequest.getReturnUrl()
             )
         );
         registerCallbacks(configuration, payPalRequest, payPalResponse);
     }

    private void registerCallbacks(
            final Configuration configuration,
            final PayPalNativeRequest payPalRequest,
            final PayPalNativeCheckoutResponse payPalResponse
    ) {
        PayPalCheckout.registerCallbacks(
                approval -> {
                    PayPalNativeCheckoutAccount payPalAccount = setupAccount(configuration, payPalRequest, payPalResponse);

                    internalPayPalClient.tokenize(payPalAccount, (payPalAccountNonce, error) -> {
                        if (payPalAccountNonce != null) {
                            listener.onPayPalSuccess(payPalAccountNonce);
                        } else {
                            listener.onPayPalFailure(new Exception("PaypalAccountNonce is null"));
                        }
                    });

                },
                null,
                () -> listener.onPayPalFailure(new Exception("User has canceled")),
                errorInfo -> listener.onPayPalFailure(new Exception(errorInfo.getError().getMessage()))
        );
    }

    private PayPalNativeCheckoutAccount setupAccount(
            final Configuration configuration,
            final PayPalNativeRequest payPalRequest,
            final PayPalNativeCheckoutResponse payPalResponse
    ) {
        PayPalNativeCheckoutAccount payPalAccount = new PayPalNativeCheckoutAccount();

        String merchantAccountId = payPalRequest.getMerchantAccountId();
        String paymentType = payPalRequest instanceof PayPalNativeCheckoutVaultRequest ? "billing-agreement" : "single-payment";
        payPalAccount.setClientMetadataId(configuration.getPayPalClientId());
        payPalAccount.setPaymentType(paymentType);

        if (merchantAccountId != null) {
            payPalAccount.setMerchantAccountId(merchantAccountId);
        }

        return payPalAccount;
    }
}
