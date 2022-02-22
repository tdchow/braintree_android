package com.braintreepayments.demo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.braintreepayments.api.Authorization;
import com.braintreepayments.api.BraintreeClient;
import com.braintreepayments.api.BraintreeHttpClient;
import com.braintreepayments.api.PaymentMethodNonce;
import com.braintreepayments.api.BrowserSwitchResult;
import com.braintreepayments.api.DataCollector;
import com.braintreepayments.api.PayPalClient;
import com.braintreepayments.api.ThreadScheduler;

import static com.braintreepayments.demo.PayPalRequestFactory.createPayPalCheckoutRequest;
import static com.braintreepayments.demo.PayPalRequestFactory.createPayPalVaultRequest;

import org.json.JSONObject;

public class PayPalFragment extends BaseFragment {

    private String deviceData;
    private PayPalClient payPalClient;
    private DataCollector dataCollector;

    private Button billingAgreementButton;
    private Button singlePaymentButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_paypal, container, false);
        billingAgreementButton = view.findViewById(R.id.paypal_billing_agreement_button);
        singlePaymentButton = view.findViewById(R.id.paypal_single_payment_button);

        billingAgreementButton.setOnClickListener(this::launchBillingAgreement);
        singlePaymentButton.setOnClickListener(this::launchSinglePayment);

        DemoViewModel viewModel = new ViewModelProvider(getActivity()).get(DemoViewModel.class);
        viewModel.getPayPalBrowserSwitchResult().observe(getViewLifecycleOwner(), this::handlePayPalBrowserSwitchResult);

        return view;
    }

    public void launchSinglePayment(View v) {
        launchPayPal(false);
    }

    public void launchBillingAgreement(View v) {
        launchPayPal(true);
    }

    private void launchPayPal(boolean isBillingAgreement) {
//        FragmentActivity activity = getActivity();
//        activity.setProgressBarIndeterminateVisibility(true);
//
//        BraintreeClient braintreeClient = getBraintreeClient();
//        payPalClient = new PayPalClient(braintreeClient);
//        dataCollector = new DataCollector(braintreeClient);
//
//        braintreeClient.getConfiguration((configuration, configError) -> {
//            if (getActivity().getIntent().getBooleanExtra(MainFragment.EXTRA_COLLECT_DEVICE_DATA, false)) {
//                dataCollector.collectDeviceData(activity, (deviceData, dataCollectorError) -> this.deviceData = deviceData);
//            }
//            if (isBillingAgreement) {
//                payPalClient.tokenizePayPalAccount(activity, createPayPalVaultRequest(activity), payPalError -> {
//                    if (payPalError != null) {
//                        handleError(payPalError);
//                    }
//                });
//            } else {
//                payPalClient.tokenizePayPalAccount(activity, createPayPalCheckoutRequest(activity, "1.00"), payPalError -> {
//                    if (payPalError != null) {
//                        handleError(payPalError);
//                    }
//                });
//            }
//        });

        BraintreeHttpClient httpClient = new BraintreeHttpClient();
        ThreadScheduler scheduler = new ThreadScheduler();
        scheduler.runOnBackground(new Runnable() {
            @Override
            public void run() {

                try {
                    JSONObject billingAddress = new JSONObject()
                            .put("address_line_1", "Kantstraße 70")
                            .put("address_line_2", "#170")
                            .put("admin_area_1", "Freistaat Sachsen")
                            .put("admin_area_2", "Annaberg-buchholz")
                            .put("postal_code", "09456")
                            .put("country_code", "FR");
                    JSONObject data = new JSONObject()
                            .put("account_holder_name", "John Doe")
                            .put("customer_id", "a-customer-id")
                            .put("iban", "FR7618106000321234566666610")
                            .put("mandate_type", "RECURRENT")
                            .put("billing_address", billingAddress);
                    JSONObject data2 = new JSONObject()
                            .put("sepa_debit", data)
                            .put("cancel_url", "https://example.com")
                            .put("return_url", "https://example.com")
                            .put("merchant_account_id", "eur_pwpp_multi_account_merchant_account");

                    String result = httpClient.post("merchants/pwpp_multi_account_merchant/client_api/v1/sepa_debit", data2.toString(), null, Authorization.fromString("development_testing_pwpp_multi_account_merchant"));
                    Log.d("RESULT", result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void handlePayPalResult(PaymentMethodNonce paymentMethodNonce, Exception error) {
        if (paymentMethodNonce != null) {
            super.onPaymentMethodNonceCreated(paymentMethodNonce);

            PayPalFragmentDirections.ActionPayPalFragmentToDisplayNonceFragment action =
                PayPalFragmentDirections.actionPayPalFragmentToDisplayNonceFragment(paymentMethodNonce);
            action.setDeviceData(deviceData);

            NavHostFragment.findNavController(this).navigate(action);
        }
    }

    public void handlePayPalBrowserSwitchResult(BrowserSwitchResult browserSwitchResult) {
        if (browserSwitchResult != null) {
            payPalClient.onBrowserSwitchResult(browserSwitchResult, (payPalAccountNonce, error) -> handlePayPalResult(payPalAccountNonce, error));
        }
    }
}
