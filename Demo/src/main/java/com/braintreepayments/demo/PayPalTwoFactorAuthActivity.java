package com.braintreepayments.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.braintreepayments.api.BraintreeFragment;
import com.braintreepayments.api.PayPal;
import com.braintreepayments.api.PayPalTwoFactorAuth;
import com.braintreepayments.api.exceptions.InvalidArgumentException;
import com.braintreepayments.api.interfaces.PayPalTwoFactorAuthCallback;
import com.braintreepayments.api.models.PayPalRequest;
import com.braintreepayments.api.models.PayPalTwoFactorAuthRequest;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.braintreepayments.api.models.PostalAddress;

public class PayPalTwoFactorAuthActivity extends BaseActivity {

    private EditText mAmountEditText;
    private EditText mNonceEditText;
    private Button mBillingAgreementButton;
    private Button mPayPalTwoFactorAuthButton;

    private boolean billingAgreement = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal_two_factor_auth);

        mAmountEditText = findViewById(R.id.amount_edit_text);
        mNonceEditText = findViewById(R.id.nonce_edit_text);
        mBillingAgreementButton = findViewById(R.id.paypal_billing_agreement_button);
        mPayPalTwoFactorAuthButton = findViewById(R.id.paypal_two_factor_auth_button);
    }

    @Override
    public void onPaymentMethodNonceCreated(PaymentMethodNonce paymentMethodNonce) {
        super.onPaymentMethodNonceCreated(paymentMethodNonce);
        if (billingAgreement) {
            // send to server and get the second nonce thingy
            mNonceEditText.setText("got a nonce!");
            billingAgreement = false;
        } else {
            Intent intent = new Intent()
                    .putExtra(MainActivity.EXTRA_PAYMENT_RESULT, paymentMethodNonce);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    protected void onAuthorizationFetched() {
        try {
            mBraintreeFragment = BraintreeFragment.newInstance(this, mAuthorization);
            enableButtons(true);
        } catch (InvalidArgumentException e) {
            onError(e);
        }
    }

    @Override
    protected void reset() {
        mNonceEditText.setText("");
        enableButtons(false);
    }

    private void enableButtons(boolean enabled) {
        mPayPalTwoFactorAuthButton.setEnabled(enabled);
        mBillingAgreementButton.setEnabled(enabled);
    }

    public void launchBillingAgreement(View v) {
        billingAgreement = true;
        setProgressBarIndeterminateVisibility(true);

        PayPal.requestBillingAgreement(mBraintreeFragment, getPayPalRequest());
    }

    public void onPayPalTwoFactorAuthButtonPress(View v) {
        PayPalTwoFactorAuthRequest request = new PayPalTwoFactorAuthRequest()
                .amount(mAmountEditText.getText().toString())
                .nonce(mNonceEditText.getText().toString())
                .currencyCode("INR"); // for now, our demo defaults to INR currency

        PayPalTwoFactorAuth.performTwoFactorLookup(mBraintreeFragment, request, new PayPalTwoFactorAuthCallback() {
            @Override
            public void onLookupResult(@NonNull PaymentMethodNonce nonce) {
                PayPalTwoFactorAuth.continueTwoFactorAuthentication(mBraintreeFragment, nonce);
            }

            @Override
            public void onLookupFailure(@NonNull Exception exception) {
                // notify error
                onError(exception);
            }
        });
    }

    private PayPalRequest getPayPalRequest() {
        PayPalRequest request = new PayPalRequest();
        request.intent(PayPalRequest.INTENT_AUTHORIZE);
        return request;
    }
}
