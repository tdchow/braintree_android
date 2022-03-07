package com.braintreepayments.api;

import android.content.Context;
import android.net.Uri;
import android.util.Base64;

import androidx.annotation.VisibleForTesting;

import org.json.JSONException;

class ConfigurationLoader {

    private final BraintreeHttpClient httpClient;
    private final ConfigurationCache configurationCache;

    ConfigurationLoader(BraintreeHttpClient httpClient) {
        this(httpClient, ConfigurationCache.getInstance());
    }

    @VisibleForTesting
    ConfigurationLoader(BraintreeHttpClient httpClient, ConfigurationCache configurationCache) {
        this.httpClient = httpClient;
        this.configurationCache = configurationCache;
    }

    void loadConfiguration(final Context context, final Authorization authorization, final ConfigurationCallback callback) {
        if (authorization instanceof InvalidAuthorization) {
            String message = ((InvalidAuthorization) authorization).getErrorMessage();
            callback.onResult(null, new BraintreeException(message));
            return;
        }

        final String configUrl = Uri.parse(authorization.getConfigUrl())
                .buildUpon()
                .appendQueryParameter("configVersion", "3")
                .build()
                .toString();

        Configuration cachedConfig = getCachedConfiguration(context, authorization, configUrl);
        if (cachedConfig != null) {
            callback.onResult(cachedConfig, null);
        } else {

            // TEMP TEST BLOCK ONLY
            // To Bypass need for QA Client token
            // DO NOT MERGE
//            final String TEST_CONFIGURATION_WITH_GRAPHQL = "{" +
//                    "     \"authorizationFingerprint\":\"eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiIsImtpZCI6IjIwMTgwNDI2MTYtcWEiLCJpc3MiOiJodHRwczovL3FhLmJyYWludHJlZXBheW1lbnRzLmNvbSJ9.eyJleHAiOjE2NDY3NTM1NTgsImp0aSI6IjE2NWQyNGMyLTEwMzYtNDRmZi1iOTM3LWU1MGFiYjY2NDY5YiIsInN1YiI6ImludGVncmF0aW9uX21lcmNoYW50X2lkIiwiaXNzIjoiaHR0cHM6Ly9xYS5icmFpbnRyZWVwYXltZW50cy5jb20iLCJtZXJjaGFudCI6eyJwdWJsaWNfaWQiOiJpbnRlZ3JhdGlvbl9tZXJjaGFudF9pZCIsInZlcmlmeV9jYXJkX2J5X2RlZmF1bHQiOmZhbHNlfSwicmlnaHRzIjpbIm1hbmFnZV92YXVsdCJdLCJzY29wZSI6WyJCcmFpbnRyZWU6VmF1bHQiXSwib3B0aW9ucyI6e319.v77WWbLxxghC2JU2U_sEs9VO6oTg1zQTkLE3YsZFajVZj1ntXMntsf0SHJUgUsXzQAb4Vw1T0KoMAOWJUMNjkQ\"," +
//                    "     \"clientApiUrl\": \"client_api_url\"," +
//                    "     \"environment\": \"test\"," +
//                    "     \"merchantId\": \"integration_merchant_id\"," +
//                    "     \"merchantAccountId\": \"integration_merchant_account_id\"," +
//                    "     \"graphQL\": {" +
//                    "      \"url\": \"https://payments-qa.dev.braintree-api.com/graphql\"," +
//                    "      \"features\": [\"tokenize_credit_cards\"]" +
//                    "  }" +
//                    "}";
//
//            try {
//                Configuration configuration = Configuration.fromJson(TEST_CONFIGURATION_WITH_GRAPHQL);
//                saveConfigurationToCache(context, configuration, authorization, configUrl);
//                callback.onResult(configuration, null);
//            } catch (JSONException jsonException) {
//                callback.onResult(null, jsonException);
//            }

            httpClient.get(configUrl, null, authorization, HttpClient.RETRY_MAX_3_TIMES, new HttpResponseCallback() {

                @Override
                public void onResult(String responseBody, Exception httpError) {
                    if (responseBody != null) {
                        try {
                            Configuration configuration = Configuration.fromJson(responseBody);
                            saveConfigurationToCache(context, configuration, authorization, configUrl);
                            callback.onResult(configuration, null);
                        } catch (JSONException jsonException) {
                            callback.onResult(null, jsonException);
                        }
                    } else {
                        String errorMessageFormat = "Request for configuration has failed: %s";
                        String errorMessage = String.format(errorMessageFormat, httpError.getMessage());

                        ConfigurationException configurationException = new ConfigurationException(errorMessage, httpError);
                        callback.onResult(null, configurationException);
                    }
                }
            });
        }
    }

    private void saveConfigurationToCache(Context context, Configuration configuration, Authorization authorization, String configUrl) {
        String cacheKey = createCacheKey(authorization, configUrl);
        configurationCache.saveConfiguration(context, configuration, cacheKey);
    }

    private Configuration getCachedConfiguration(Context context, Authorization authorization, String configUrl) {
        String cacheKey = createCacheKey(authorization, configUrl);
        String cachedConfigResponse = configurationCache.getConfiguration(context, cacheKey);
        try {
            return Configuration.fromJson(cachedConfigResponse);
        } catch (JSONException e) {
            return null;
        }
    }

    private static String createCacheKey(Authorization authorization, String configUrl) {
        return Base64.encodeToString(String.format("%s%s", configUrl, authorization.getBearer()).getBytes(), 0);
    }
}
