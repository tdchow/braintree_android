package com.braintreepayments.api

import org.junit.Assert.*
import org.junit.Test

class ConfigurationUnitTestKt {

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
}