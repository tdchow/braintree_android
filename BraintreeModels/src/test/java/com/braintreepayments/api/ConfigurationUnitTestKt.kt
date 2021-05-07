package com.braintreepayments.api

import org.junit.Assert.*
import org.junit.Test

class ConfigurationUnitTestKt {

    @Test
    fun graphQLUrl_whenPropertyExists_returnsItsValue() {
        // language=JSON
        val json = """{ "graphQL": { "url":  "https://graphql.com" }}"""
        val sut = Configuration.fromJson(json)
        assertEquals("https://graphql.com", sut.graphQLUrl)
    }

    @Test
    fun graphQLUrl_whenPropertyDoesNotExist_returnsEmptyString() {
        val sut = Configuration.fromJson("")
        assertEquals("", sut.graphQLUrl)
    }

    @Test
    fun isGraphQLEnabled_whenGraphQLUrlExists_returnsTrue() {
        // language=JSON
        val json = """{ "graphQL": { "url":  "https://graphql.com" }}"""
        val sut = Configuration.fromJson(json)
        assertTrue(sut.isGraphQLEnabled)
    }

    @Test
    fun isGraphQLEnabled_whenGraphQLUrlIsEmpty_returnsFalse() {
        // language=JSON
        val json = """{ "graphQL": { "url":  "" }}"""
        val sut = Configuration.fromJson(json)
        assertFalse(sut.isGraphQLEnabled)
    }

    @Test
    fun isGraphQLFeatureEnabled_whenFeatureExistsInGraphQLConfig_returnsTrue() {
        // language=JSON
        val json = """{ "graphQL": { "features":  [ "a-feature" ] }}"""
        val sut = Configuration.fromJson(json)
        assertTrue(sut.isGraphQLFeatureEnabled("a-feature"))
    }

    @Test
    fun isGraphQLFeatureEnabled_whenFeatureNotPresentInGraphQLConfig_returnsFalse() {
        val sut = Configuration.fromJson("")
        assertFalse(sut.isGraphQLFeatureEnabled("a-feature"))
    }

    @Test
    fun analyticsUrl_whenPropertyExists_returnsItsValue() {
        // language=JSON
        val json = """{ "analyticsUrl": "https://analytics.com" }"""
        val sut = Configuration.fromJson(json)
        assertEquals("https://analytics.com", sut.analyticsUrl)
    }

    @Test
    fun analyticsUrl_whenPropertyDoesNotExist_returnsNull() {
        val sut = Configuration.fromJson("")
        assertNull(sut.analyticsUrl)
    }

    @Test
    fun isAnalyticsEnabled_whenAnalyticsUrlIsPresent_returnsItsValue() {
        // language=JSON
        val json = """{ "analyticsUrl": "https://analytics.com" }"""
        val sut = Configuration.fromJson(json)
        assertTrue(sut.isAnalyticsEnabled)
    }

    @Test
    fun isAnalyticsEnabled_whenAnalyticsUrlDoesNotExist_returnsFalse() {
        val sut = Configuration.fromJson("")
        assertFalse(sut.isAnalyticsEnabled)
    }

    @Test
    fun isFraudDataCollectionEnabled_whenPropertyExists_returnsItsValue() {
        // language=JSON
        val json = """{ "creditCards": { "collectDeviceData": true }}"""
        val sut = Configuration.fromJson(json)
        assertTrue(sut.isFraudDataCollectionEnabled)
    }

    @Test
    fun isFraudDataCollectionEnabled_whenPropertyDoesNotExist_returnsFalse() {
        val sut = Configuration.fromJson("")
        assertFalse(sut.isFraudDataCollectionEnabled)
    }

    @Test
    fun supportedCardTypes_whenPropertyExists_returnsListOfSupportedCards() {
        // language=JSON
        val json = """{
            "creditCards": {
              "supportedCardTypes": [ "American Express", "Discover", "JCB", "MasterCard", "Visa" ]
            }
        }"""
        val sut = Configuration.fromJson(json)

        val expectedSupportedCards =
            listOf("American Express", "Discover", "JCB", "MasterCard", "Visa")
        assertEquals(expectedSupportedCards, sut.supportedCardTypes)
    }

    @Test
    fun supportedCardTypes_whenPropertyDoesNotExist_returnsEmptyList() {
        val sut = Configuration.fromJson("")
        assertEquals(emptyList<String>(), sut.supportedCardTypes)
    }

    @Test
    fun kountMerchantId_whenPropertyExists_returnsItsValue() {
        // language=JSON
        val json = """{ "kount": { "kountMerchantid": "kount-merchant-id" }}"""
        val sut = Configuration.fromJson(json)
        assertEquals("kount-merchant-id", sut.kountMerchantId)
    }

    @Test
    fun kountMerchantId_whenPropertyDoesNotExist_returnsEmptyString() {
        val sut = Configuration.fromJson("")
        assertEquals("", sut.kountMerchantId)
    }

    @Test
    fun isKountEnabled_whenKountMerchantIdExists_returnsTrue() {
        // language=JSON
        val json = """{ "kount": { "kountMerchantid": "kount-merchant-id" }}"""
        val sut = Configuration.fromJson(json)
        assertTrue(sut.isKountEnabled)
    }

    @Test
    fun isKountEnabled_whenKountMerchantIdIsEmpty_returnsFalse() {
        // language=JSON
        val json = """{ "kount": { "kountMerchantid": "" }}"""
        val sut = Configuration.fromJson(json)
        assertFalse(sut.isKountEnabled)
    }

    @Test
    fun samsungPayAuthorization_whenPropertyExists_returnsItsValue() {
        // language=JSON
        val json = """{ "samsungPay": { "samsungAuthorization": "samsung-authorization" }}"""
        val sut = Configuration.fromJson(json)
        assertEquals("samsung-authorization", sut.samsungPayAuthorization)
    }

    @Test
    fun samsungPayAuthorization_whenPropertyDoesNotExist_returnsEmptyString() {
        val sut = Configuration.fromJson("")
        assertEquals("", sut.samsungPayAuthorization)
    }

    @Test
    fun isSamsungPayEnabled_whenSamsungPayAuthorizationExists_returnsItsValue() {
        // language=JSON
        val json = """{ "samsungPay": { "samsungAuthorization": "samsung-authorization" }}"""
        val sut = Configuration.fromJson(json)
        assertTrue(sut.isSamsungPayEnabled)
    }

    @Test
    fun samsungPayAuthorization_whenSamsungPayAuthorizationIsEmpty_returnsFalse() {
        // language=JSON
        val json = """{ "samsungPay": { "samsungAuthorization": "" }}"""
        val sut = Configuration.fromJson(json)
        assertFalse(sut.isSamsungPayEnabled)
    }

    @Test
    fun samsungPayMerchantDisplayName_whenPropertyExists_returnsItsValue() {
        // language=JSON
        val json = """{ "samsungPay": { "displayName": "samsung-display-name" }}"""
        val sut = Configuration.fromJson(json)
        assertEquals("samsung-display-name", sut.samsungPayMerchantDisplayName)
    }

    @Test
    fun samsungPayMerchantDisplayName_whenPropertyDoesNotExist_returnsEmptyString() {
        val sut = Configuration.fromJson("")
        assertEquals("", sut.samsungPayMerchantDisplayName)
    }

    @Test
    fun samsungPayServiceId_whenPropertyExists_returnsItsValue() {
        // language=JSON
        val json = """{ "samsungPay": { "serviceId": "samsung-service-id" }}"""
        val sut = Configuration.fromJson(json)
        assertEquals("samsung-service-id", sut.samsungPayMerchantDisplayName)
    }

    @Test
    fun samsungPayServiceId_whenPropertyDoesNotExist_returnsEmptyString() {
        val sut = Configuration.fromJson("")
        assertEquals("", sut.samsungPayMerchantDisplayName)
    }

    @Test
    fun samsungPayEnvironment_whenPropertyExists_returnsItsValue() {
        // language=JSON
        val json = """{ "samsungPay": { "environment": "samsung-environment" }}"""
        val sut = Configuration.fromJson(json)
        assertEquals("samsung-environment", sut.samsungPayEnvironment)
    }

    @Test
    fun samsungPayEnvironment_whenPropertyDoesNotExist_returnsEmptyString() {
        val sut = Configuration.fromJson("")
        assertEquals("", sut.samsungPayEnvironment)
    }

    @Test
    fun samsungPaySupportedCardBrands_whenPropertyExists_returnsListOfSupportedCardBrands() {
        // language=JSON
        val json = """{
            "samsungPay": {
                "supportedCardBrands": [
                    "american_express",
                    "diners",
                    "discover",
                    "jcb",
                    "maestro",
                    "mastercard",
                    "visa"
                ]
            }
        }"""
        val sut = Configuration.fromJson(json)

        val expectedCardBrands = listOf(
                "american_express", "diners", "discover", "jcb", "maestro", "mastercard", "visa")
        assertEquals(expectedCardBrands, sut.samsungPaySupportedCardBrands)
    }

    @Test
    fun samsungPaySupportedCardBrands_whenPropertyDoesNotExist_returnsEmptyList() {
        val sut = Configuration.fromJson("")
        assertEquals(emptyList<String>(), sut.samsungPaySupportedCardBrands)
    }

    @Test
    fun isPayPalEnabled_whenPropertyExists_returnsItsValue() {
        // language=JSON
        val json = """{ "paypalEnabled": true }"""
        val sut = Configuration.fromJson(json)
        assertTrue(sut.isPayPalEnabled)
    }

    @Test
    fun isPayPalEnabled_whenPropertyDoesNotExist_returnsFalse() {
        val sut = Configuration.fromJson("")
        assertFalse(sut.isPayPalEnabled)
    }

    @Test
    fun payPalDisplayName_whenPropertyExists_returnsItsValue() {
        // language=JSON
        val json = """{ "paypal": { "displayName": "paypayl-display-name" }}"""
        val sut = Configuration.fromJson(json)
        assertEquals("paypayl-display-name", sut.payPalDisplayName)
    }

    @Test
    fun payPalDisplayName_whenPropertyDoesNotExist_returnsNull() {
        val sut = Configuration.fromJson("")
        assertNull(sut.payPalDisplayName)
    }

    @Test
    fun payPalClientId_whenPropertyExists_returnsItsValue() {
        // language=JSON
        val json = """{ "paypal": { "clientId": "paypayl-client-id" }}"""
        val sut = Configuration.fromJson(json)
        assertEquals("paypayl-client-id", sut.payPalDisplayName)
    }

    @Test
    fun payPalClientId_whenPropertyDoesNotExist_returnsNull() {
        val sut = Configuration.fromJson("")
        assertNull(sut.payPalClientId)
    }

    @Test
    fun payPalPrivacyUrl_whenPropertyExists_returnsItsValue() {
        // language=JSON
        val json = """{ "paypal": { "privacyUrl": "https://paypal.privacy.com" }}"""
        val sut = Configuration.fromJson(json)
        assertEquals("https://paypal.privacy.com", sut.payPalPrivacyUrl)
    }

    @Test
    fun payPalPrivacyUrl_whenPropertyDoesNotExist_returnsNull() {
        val sut = Configuration.fromJson("")
        assertNull(sut.payPalPrivacyUrl)
    }

    @Test
    fun payPalUserAgreement_whenPropertyExists_returnsItsValue() {
        // language=JSON
        val json = """{ "paypal": { "userAgreementUrl": "https://paypal.user-agreement.com" }}"""
        val sut = Configuration.fromJson(json)
        assertEquals("https://paypal.user-agreement.com", sut.payPalUserAgreementUrl)
    }

    @Test
    fun payPalUserAgreement_whenPropertyDoesNotExist_returnsNull() {
        val sut = Configuration.fromJson("")
        assertNull(sut.payPalUserAgreementUrl)
    }

    @Test
    fun payPalDirectBaseUrl_whenPropertyExists_returnsItsValue() {
        // language=JSON
        val json = """{ "paypal": { "directBaseUrl": "https://paypal.direct-base-url.com" }}"""
        val sut = Configuration.fromJson(json)
        assertEquals("https://paypal.direct-base-url.com", sut.payPalUserAgreementUrl)
    }

    @Test
    fun payPalDirectBaseUrl_whenPropertyDoesNotExist_returnsNull() {
        val sut = Configuration.fromJson("")
        assertNull(sut.payPalDirectBaseUrl)
    }

    @Test
    fun payPalEnvironment_whenPropertyExists_returnsItsValue() {
        // language=JSON
        val json = """{ "paypal": { "environment": "paypal-environment" }}"""
        val sut = Configuration.fromJson(json)
        assertEquals("paypal-environment", sut.payPalEnvironment)
    }

    @Test
    fun payPalEnvironment_whenPropertyDoesNotExist_returnsNull() {
        val sut = Configuration.fromJson("")
        assertNull(sut.payPalEnvironment)
    }

    @Test
    fun payPalCurrencyIsoCode_whenPropertyExists_returnsItsValue() {
        // language=JSON
        val json = """{ "paypal": { "currencyIsoCode": "currency-iso-code" }}"""
        val sut = Configuration.fromJson(json)
        assertEquals("currency-iso-code", sut.payPalCurrencyIsoCode)
    }

    @Test
    fun payPalCurrencyIsoCode_whenPropertyDoesNotExist_returnsNull() {
        val sut = Configuration.fromJson("")
        assertNull(sut.payPalCurrencyIsoCode)
    }

    @Test
    fun isPayPalTouchDisabled_whenPropertyExists_returnsItsValue() {
        // language=JSON
        val json = """{ "paypal": { "touchDisabled": "touch-disabled" }}"""
        val sut = Configuration.fromJson(json)
        assertEquals("touch-disabled", sut.isPayPalTouchDisabled)
    }

    @Test
    fun isPayPalTouchDisabled_whenPropertyDoesNotExist_returnsTrue() {
        val sut = Configuration.fromJson("")
        assertTrue(sut.isPayPalTouchDisabled)
    }

    @Test
    fun visaCheckoutApiKey_whenPropertyExists_returnsItsValue() {
        // language=JSON
        val json = """{ "visaCheckout": { "apikey": "api-key" }}"""
        val sut = Configuration.fromJson(json)
        assertEquals("api-key", sut.visaCheckoutApiKey)
    }

    @Test
    fun visaCheckoutApiKey_whenPropertyDoesNotExist_returnsEmptyString() {
        val sut = Configuration.fromJson("")
        assertEquals("", sut.visaCheckoutApiKey)
    }

    @Test
    fun isVisaCheckoutEnabled_whenVisaCheckoutApiKeyIsValid_returnsTrue() {
        // language=JSON
        val json = """{ "visaCheckout": { "apikey": "api-key" }}"""
        val sut = Configuration.fromJson(json)
        assertTrue(sut.isVisaCheckoutEnabled)
    }

    @Test
    fun isVisaCheckoutEnabled_whenVisaCheckoutApiKeyIsEmpty_returnsFalse() {
        // language=JSON
        val json = """{ "visaCheckout": { "apikey": "" }}"""
        val sut = Configuration.fromJson(json)
        assertFalse(sut.isVisaCheckoutEnabled)
    }

    @Test
    fun visaCheckoutExternalClientId_whenPropertyExists_returnsItsValue() {
        // language=JSON
        val json = """{ "visaCheckout": { "externalClientId": "external-client-id" }}"""
        val sut = Configuration.fromJson(json)
        assertEquals("external-client-id", sut.visaCheckoutExternalClientId)
    }

    @Test
    fun visaCheckoutExternalClientId_whenPropertyDoesNotExist_returnsEmptyString() {
        val sut = Configuration.fromJson("")
        assertEquals("", sut.visaCheckoutExternalClientId)
    }

    @Test
    fun visaCheckoutAcceptedCardBrands_whenPropertyExists_returnsListOfAcceptedCardBrands() {
        // language=JSON
        val json = """{
            "visaCheckout": {
                "supportedCardTypes": [ "American Express", "Visa", "Discover", "MasterCard" ]
            }
        }"""
        val sut = Configuration.fromJson(json)
        assertEquals(listOf("AMEX", "VISA", "DISCOVER", "MASTERCARD"), sut.visaCheckoutSupportedNetworks)
    }

    @Test
    fun visaCheckoutAcceptedCardBrands_whenPropertyDoesNotExist_returnsEmptyList() {
        val sut = Configuration.fromJson("")
        assertEquals(emptyList<String>(), sut.visaCheckoutSupportedNetworks)
    }

    @Test
    fun venmoAccessToken_whenPropertyExists_returnsItsValue() {
        // language=JSON
        val json = """{ "payWithVenmo": { "accessToken": "venmo-access-token" }}"""
        val sut = Configuration.fromJson(json)
        assertEquals("venmo-access-token", sut.visaCheckoutExternalClientId)
    }

    @Test
    fun venmoAccessToken_whenPropertyDoesNotExist_returnsEmptyString() {
        val sut = Configuration.fromJson("")
        assertEquals("", sut.visaCheckoutExternalClientId)
    }

    @Test
    fun isVenmoEnabled_whenAccessTokenIsPresent_returnsTrue() {
        // language=JSON
        val json = """{ "payWithVenmo": { "accessToken": "venmo-access-token" }}"""
        val sut = Configuration.fromJson(json)
        assertTrue(sut.isVenmoEnabled)
    }

    @Test
    fun isVenmoEnabled_whenAccessTokenIsEmpty_returnsFalse() {
        // language=JSON
        val json = """{ "payWithVenmo": { "accessToken": "" }}"""
        val sut = Configuration.fromJson(json)
        assertFalse(sut.isVenmoEnabled)
    }

    @Test
    fun venmoEnvironment_whenPropertyExists_returnsItsValue() {
        // language=JSON
        val json = """{ "payWithVenmo": { "environment": "venmo-environment" }}"""
        val sut = Configuration.fromJson(json)
        assertEquals("venmo-environment", sut.venmoEnvironment)
    }

    @Test
    fun venmoEnvironment_whenPropertyDoesNotExist_returnsFalse() {
        val sut = Configuration.fromJson("")
        assertEquals("", sut.venmoEnvironment)
    }

    @Test
    fun venmoMerchantId_whenPropertyExists_returnsItsValue() {
        // language=JSON
        val json = """{ "payWithVenmo": { "merchantId": "venmo-merchant-id" }}"""
        val sut = Configuration.fromJson(json)
        assertEquals("venmo-merchant-id", sut.venmoMerchantId)
    }

    @Test
    fun venmoMerchantId_whenPropertyDoesNotExist_returnsEmptyString() {
        val sut = Configuration.fromJson("")
        assertEquals("", sut.venmoMerchantId)
    }
}