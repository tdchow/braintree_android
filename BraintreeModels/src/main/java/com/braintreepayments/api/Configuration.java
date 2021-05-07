package com.braintreepayments.api;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Contains the remote configuration for the Braintree Android SDK.
 */
public class Configuration {

    private final String configurationString;
    private final Set<String> challenges;

    private final BTJSON json;

    /**
     * Creates a new {@link Configuration} instance from a json string.
     *
     * @param configurationString The json configuration string from Braintree.
     * @return {@link Configuration} instance.
     */
    public static Configuration fromJson(@Nullable String configurationString) throws JSONException {
        return new Configuration(configurationString);
    }

    protected Configuration(@Nullable String configurationString) throws JSONException {
        if (configurationString == null) {
            throw new JSONException("Configuration cannot be null");
        }

        this.configurationString = configurationString;

        json = new BTJSON(configurationString);
        challenges = new HashSet<>(json.getStrings("$.challenges[*]", emptyList()));
    }

    public String toJson() {
        return configurationString;
    }

    /**
     * @return The assets URL of the current environment.
     */
    public String getAssetsUrl() {
        return json.getString("$.assetsUrl", "");
    }

    /**
     * @return The url of the Braintree client API for the current environment.
     */
    public String getClientApiUrl() {
        return json.getString("$.clientApiUrl");
    }

    /**
     * @return {@code true} if cvv is required for card transactions, {@code false} otherwise.
     */
    public boolean isCvvChallengePresent() {
        return challenges.contains("cvv");
    }

    /**
     * @return {@code true} if postal code is required for card transactions, {@code false} otherwise.
     */
    public boolean isPostalCodeChallengePresent() {
        return challenges.contains("postal_code");
    }

    /**
     * @return {@code true} if fraud device data collection should occur; {@code false} otherwise.
     */
    boolean isFraudDataCollectionEnabled() {
        return json.getBoolean("$.creditCards.collectDeviceData", false);
    }

    /**
     * @return {@code true} if Venmo is enabled for the merchant account; {@code false} otherwise.
     */
    public boolean isVenmoEnabled() {
        return !TextUtils.isEmpty(getVenmoAccessToken());
    }

    /**
     * @return the Access Token used by the Venmo app to tokenize on behalf of the merchant.
     */
    String getVenmoAccessToken() {
        return json.getString("$.payWithVenmo.accessToken", "");
    }

    /**
     * @return the Venmo merchant id used by the Venmo app to authorize payment.
     */
    String getVenmoMerchantId() {
        return json.getString("$.payWithVenmo.merchantId", "");
    }

    /**
     * @return the Venmo environment used to handle this payment.
     */
    String getVenmoEnvironment() {
        return json.getString("$.payWithVenmo.environment", "");
    }

    /**
     * @return {@code true} if GraphQL is enabled for the merchant account; {@code false} otherwise.
     */
    boolean isGraphQLEnabled() {
        return !TextUtils.isEmpty(getGraphQLUrl());
    }

    /**
     * @return {@code true} if Local Payment is enabled for the merchant account; {@code false} otherwise.
     */
    public boolean isLocalPaymentEnabled() {
        // Local Payments are enabled when PayPal is enabled
        return isPayPalEnabled();
    }

    /**
     * @return {@code true} if Kount is enabled for the merchant account; {@code false} otherwise.
     */
    boolean isKountEnabled() {
        return !TextUtils.isEmpty(getKountMerchantId());
    }

    /**
     * @return the Kount merchant id set in the Gateway.
     */
    String getKountMerchantId() {
        return json.getString("$.kount.kountMerchantId", "");
    }

    /**
     * @return {@code true} if UnionPay is enabled for the merchant account; {@code false} otherwise.
     */
    public boolean isUnionPayEnabled() {
        return json.getBoolean("$.unionPay.enabled", false);
    }

    /**
     * @return The current environment.
     */
    public String getEnvironment() {
        return json.getString("$.environment");
    }

    /**
     * @return {@code true} if PayPal is enabled and supported in the current environment,
     *         {@code false} otherwise.
     */
    public boolean isPayPalEnabled() {
        return json.getBoolean("$.paypalEnabled", false);
    }

    /**
     * @return the PayPal app display name.
     */
    String getPayPalDisplayName() {
        return json.getString("$.paypal.displayName", null);
    }

    /**
     * @return the PayPal app client id.
     */
    String getPayPalClientId() {
        return json.getString("$.paypal.clientId", null);
    }

    /**
     * @return the PayPal app privacy url.
     */
    public String getPayPalPrivacyUrl() {
        return json.getString("$.paypal.privacyUrl", null);
    }

    /**
     * @return the PayPal app user agreement url.
     */
    public String getPayPalUserAgreementUrl() {
        return json.getString("$.paypal.userAgreementUrl", null);
    }

    /**
     * @return the url for custom PayPal environments.
     */
    public String getPayPalDirectBaseUrl() {
        return json.getString("$.paypal.directBaseUrl", null);
    }

    /**
     * @return the current environment for PayPal.
     */
    String getPayPalEnvironment() {
        return json.getString("$.paypal.environment", null);
    }

    /**
     * @return {@code true} if PayPal touch is currently disabled, {@code false} otherwise.
     */
    boolean isPayPalTouchDisabled() {
        return json.getBoolean("$.paypal.touchDisabled", true);
    }

    /**
     * @return the PayPal currency code.
     */
    String getPayPalCurrencyIsoCode() {
        return json.getString("$.paypal.currencyIsoCode", null);
    }

    /**
     * @return {@code true} if Google Payment is enabled and supported in the current environment; {@code false} otherwise.
     */
    public boolean isGooglePayEnabled() {
        return json.getBoolean("$.androidPay.enabled", false);
    }

    /**
     * @return the authorization fingerprint to use for Google Payment, only allows tokenizing Google Payment cards.
     */
    String getGooglePayAuthorizationFingerprint() {
        return json.getString("$.androidPay.googleAuthorizationFingerprint", null);
    }

    /**
     * @return the current Google Pay environment.
     */
    String getGooglePayEnvironment() {
        return json.getString("$.androidPay.environment", null);
    }

    /**
     * @return the Google Pay display name to show to the user.
     */
    String getGooglePayDisplayName() {
        return json.getString("$.androidPay.displayName", "");
    }

    /**
     * @return a list of supported card networks for Google Pay.
     */
    List<String> getGooglePaySupportedNetworks() {
        return json.getStrings("$.androidPay.supportedNetworks[*]", emptyList());
    }

    /**
     * @return the PayPal Client ID used by Google Pay.
     */
    String getGooglePayPayPalClientId() {
        return json.getString("$.androidPay.paypalClientId", "");
    }

    /**
     * @return {@code true} if 3D Secure is enabled and supported for the current merchant account,
     *         {@code false} otherwise.
     */
    public boolean isThreeDSecureEnabled() {
        return json.getBoolean("$.threeDSecureEnabled", false);
    }

    /**
     * @return the current Braintree merchant id.
     */
    public String getMerchantId() {
        return json.getString("$.merchantId");
    }

    /**
     * @return the current Braintree merchant account id.
     */
    public String getMerchantAccountId() {
        return json.getString("$.merchantAccountId", null);
    }

    /**
     * @return {@link String} url of the Braintree analytics service.
     */
    String getAnalyticsUrl() {
        return json.getString("$.analytics.url", null);
    }

    /**
     * @return {@code true} if analytics are enabled, {@code false} otherwise.
     */
    boolean isAnalyticsEnabled() {
        return !TextUtils.isEmpty(getAnalyticsUrl());
    }

    /**
     * @return {@code true} if Visa Checkout is enabled for the merchant account; {@code false} otherwise.
     */
    public boolean isVisaCheckoutEnabled() {
        return !TextUtils.isEmpty(getVisaCheckoutApiKey());
    }

    /**
     * @return the Visa Checkout supported networks enabled for the merchant account.
     */
    List<String> getVisaCheckoutSupportedNetworks() {
        List<String> supportedCardTypes =
            json.getStrings("$.visaCheckout.supportedCardTypes[*]", emptyList());
        return supportedCardTypesToAcceptedCardBrands(supportedCardTypes);
    }

    /**
     * @return the Visa Checkout API key configured in the Braintree Control Panel.
     */
    String getVisaCheckoutApiKey() {
        return json.getString("$.visaCheckout.apikey", "");
    }

    /**
     * @return the Visa Checkout External Client ID configured in the Braintree Control Panel.
     */
    String getVisaCheckoutExternalClientId() {
        return json.getString("$.visaCheckout.externalClientId", "");
    }

    /**
     * Check if a specific feature is enabled in the GraphQL API.
     *
     * @param feature The feature to check.
     * @return {@code true} if GraphQL is enabled and the feature is enabled, {@code false} otherwise.
     */
    boolean isGraphQLFeatureEnabled(String feature) {
        List<String> enabledFeatures = json.getStrings("$.graphQL.features", emptyList());
        return isGraphQLEnabled() && enabledFeatures.contains(feature);
    }

    /**
     * @return the GraphQL url.
     */
    String getGraphQLUrl() {
        return json.getString("$.graphQL.url", "");
    }

    /**
     * @return {@code true} if Samsung Pay is enabled; {@code false} otherwise.
     */
    public boolean isSamsungPayEnabled() {
        return !TextUtils.isEmpty(getSamsungPayAuthorization());
    }

    /**
     * @return the merchant display name for Samsung Pay.
     */
    String getSamsungPayMerchantDisplayName() {
        return json.getString("$.samsungPay.displayName", "");
    }

    /**
     * @return the Samsung Pay service id associated with the merchant.
     */
    String getSamsungPayServiceId() {
        return json.getString("$.samsungPay.serviceId", "");
    }

    /**
     * @return a list of card brands supported by Samsung Pay.
     */
    List<String> getSamsungPaySupportedCardBrands() {
        return json.getStrings("$.samsungPay.supportedCardBrands[*]", emptyList());
    }

    /**
     * @return the authorization to use with Samsung Pay.
     */
    String getSamsungPayAuthorization() {
        return json.getString("$.samsungPay.samsungAuthorization", "");
    }

    /**
     * @return the Braintree environment Samsung Pay should interact with.
     */
    String getSamsungPayEnvironment() {
        return json.getString("$.samsungPay.environment", "");
    }

    /**
     * @return the JWT for Cardinal
     */
    public String getCardinalAuthenticationJwt() {
        return json.getString("$.cardinalAuthenticationJWT", null);
    }

    /**
     * @return The Access Token for Braintree API.
     */
    String getBraintreeApiAccessToken() {
        return json.getString("$.braintreeApi.accessToken", "");
    }

    /**
     * @return the base url for accessing Braintree API.
     */
    String getBraintreeApiUrl() {
        return json.getString("$.braintreeApi.url", "");
    }

    /**
     * @return a boolean indicating whether Braintree API is enabled for this merchant.
     */
    boolean isBraintreeApiEnabled() {
        return !TextUtils.isEmpty(getBraintreeApiUrl());
    }

    /**
     * @return a {@link List<String>} of card types supported by the merchant.
     */
    List<String> getSupportedCardTypes() {
        return json.getStrings("$.creditCards.supportedCardTypes[*]", emptyList());
    }

    private static List<String> supportedCardTypesToAcceptedCardBrands(List<String> supportedCardTypes) {
        List<String> acceptedCardBrands = new ArrayList<>();

        for (String supportedCardType : supportedCardTypes) {
            switch (supportedCardType.toLowerCase(Locale.ROOT)) {
                case "visa":
                    acceptedCardBrands.add("VISA");
                    break;
                case "mastercard":
                    acceptedCardBrands.add("MASTERCARD");
                    break;
                case "discover":
                    acceptedCardBrands.add("DISCOVER");
                    break;
                case "american express":
                    acceptedCardBrands.add("AMEX");
                    break;
            }
        }

        return acceptedCardBrands;
    }

    private static List<String> emptyList() {
        return Collections.emptyList();
    }
}
