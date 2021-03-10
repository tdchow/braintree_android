package com.braintreepayments.api;

import android.os.Parcel;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class ThreeDSecureAuthenticationResponseUnitTest {

    @Test
    public void fromJson_parsesCorrectly_v1() {
        ThreeDSecureAuthenticationResponse authResponse = ThreeDSecureAuthenticationResponse.fromJson(
                Fixtures.THREE_D_SECURE_AUTHENTICATION_RESPONSE);

        assertEquals("11", authResponse.getCardNonce().getLastTwo());
        assertTrue(authResponse.getCardNonce().getThreeDSecureInfo().isLiabilityShifted());
        assertTrue(authResponse.getCardNonce().getThreeDSecureInfo().isLiabilityShiftPossible());
        assertNull(authResponse.getCardNonce().getThreeDSecureInfo().getErrorMessage());
        assertNull(authResponse.getException());
    }

    @Test
    public void fromJson_parsesCorrectly_v2() {
        ThreeDSecureAuthenticationResponse authResponse = ThreeDSecureAuthenticationResponse.fromJson(
                Fixtures.THREE_D_SECURE_V2_AUTHENTICATION_RESPONSE);

        assertEquals("91", authResponse.getCardNonce().getLastTwo());
        assertTrue(authResponse.getCardNonce().getThreeDSecureInfo().isLiabilityShifted());
        assertTrue(authResponse.getCardNonce().getThreeDSecureInfo().isLiabilityShiftPossible());
        assertNull(authResponse.getCardNonce().getThreeDSecureInfo().getErrorMessage());
        assertNull(authResponse.getException());
    }

    @Test
    public void fromJson_whenAuthenticationErrorOccurs_parsesCorrectly_v1() {
        ThreeDSecureAuthenticationResponse authResponse = ThreeDSecureAuthenticationResponse.fromJson(
                Fixtures.THREE_D_SECURE_AUTHENTICATION_RESPONSE_WITH_ERROR);

        assertNull(authResponse.getCardNonce());
        assertTrue(authResponse.getCardNonce().getThreeDSecureInfo().isLiabilityShifted());
        assertTrue(authResponse.getCardNonce().getThreeDSecureInfo().isLiabilityShiftPossible());
        assertEquals("Failed to authenticate, please try a different form of payment.", authResponse.getCardNonce().getThreeDSecureInfo().getErrorMessage());
        assertNull(authResponse.getException());
    }

    @Test
    public void fromJson_whenAuthenticationErrorOccurs_parsesCorrectly_v2() {
        ThreeDSecureAuthenticationResponse authResponse = ThreeDSecureAuthenticationResponse.fromJson(
                Fixtures.THREE_D_SECURE_V2_AUTHENTICATION_RESPONSE_WITH_ERROR);

        assertNull(authResponse.getCardNonce());
        assertTrue(authResponse.getCardNonce().getThreeDSecureInfo().isLiabilityShifted());
        assertTrue(authResponse.getCardNonce().getThreeDSecureInfo().isLiabilityShiftPossible());
        assertEquals("Failed to authenticate, please try a different form of payment.", authResponse.getCardNonce().getThreeDSecureInfo().getErrorMessage());
        assertNull(authResponse.getException());
    }

    @Test
    public void getNonceWithAuthenticationDetails_returnsNewNonce_whenAuthenticationSuccessful() throws JSONException {
        CardNonce lookupNonce = CardNonce.fromJson(Fixtures.PAYMENT_METHODS_RESPONSE_VISA_CREDIT_CARD);
        CardNonce authenticationNonce = ThreeDSecureAuthenticationResponse.getNonceWithAuthenticationDetails(
                Fixtures.THREE_D_SECURE_AUTHENTICATION_RESPONSE, lookupNonce);

        assertFalse(lookupNonce.getNonce().equalsIgnoreCase(authenticationNonce.getNonce()));
        assertNull(authenticationNonce.getThreeDSecureInfo().getErrorMessage());
    }

    //TODO: Possibly refactor after test case 13 card is usable in sand, so we can double check structure.
    @Test
    public void getNonceWithAuthenticationDetails_returnsLookupNonce_whenAuthenticationUnsuccessful_WithError() throws JSONException {
        CardNonce lookupNonce = CardNonce.fromJson(Fixtures.PAYMENT_METHODS_RESPONSE_VISA_CREDIT_CARD);
        CardNonce authenticationNonce = ThreeDSecureAuthenticationResponse.getNonceWithAuthenticationDetails(
                Fixtures.THREE_D_SECURE_AUTHENTICATION_RESPONSE_WITH_ERROR, lookupNonce);

        assertTrue(lookupNonce.getNonce().equalsIgnoreCase(authenticationNonce.getNonce()));
        assertFalse(authenticationNonce.getThreeDSecureInfo().isLiabilityShifted());
        assertFalse(authenticationNonce.getThreeDSecureInfo().isLiabilityShiftPossible());
        assertNotNull(authenticationNonce.getThreeDSecureInfo().getErrorMessage());
    }

    @Test
    public void getNonceWithAuthenticationDetails_returnsLookupNonce_whenAuthenticationUnsuccessful_WithException() throws JSONException {
        CardNonce lookupNonce = CardNonce.fromJson(Fixtures.PAYMENT_METHODS_RESPONSE_VISA_CREDIT_CARD);
        CardNonce authenticationNonce = ThreeDSecureAuthenticationResponse.getNonceWithAuthenticationDetails(
                "{'bad'}", lookupNonce);

        assertTrue(lookupNonce.getNonce().equalsIgnoreCase(authenticationNonce.getNonce()));
        assertFalse(authenticationNonce.getThreeDSecureInfo().isLiabilityShiftPossible());
        assertFalse(authenticationNonce.getThreeDSecureInfo().isLiabilityShifted());
        assertNull(authenticationNonce.getThreeDSecureInfo().getErrorMessage());
        assertEquals("Expected ':' after bad at character 7 of {'bad'}",
                authenticationNonce.getThreeDSecureInfo().getErrorMessage());
    }

    @Test
    public void isParcelable() {
        ThreeDSecureAuthenticationResponse authResponse = ThreeDSecureAuthenticationResponse.fromJson(
                Fixtures.THREE_D_SECURE_AUTHENTICATION_RESPONSE);
        Parcel parcel = Parcel.obtain();
        authResponse.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);

        ThreeDSecureAuthenticationResponse parceled = ThreeDSecureAuthenticationResponse.CREATOR.createFromParcel(parcel);

        assertEquals(authResponse.getCardNonce().getLastTwo(), parceled.getCardNonce().getLastTwo());
        assertEquals(authResponse.getCardNonce().getThreeDSecureInfo().isLiabilityShifted(),
                parceled.getCardNonce().getThreeDSecureInfo().isLiabilityShifted());
        assertEquals(authResponse.getCardNonce().getThreeDSecureInfo().isLiabilityShiftPossible(),
                parceled.getCardNonce().getThreeDSecureInfo().isLiabilityShiftPossible());
        assertEquals(authResponse.getException(), parceled.getException());
        assertEquals(authResponse.getCardNonce().getThreeDSecureInfo().isLiabilityShifted(), parceled.getCardNonce().getThreeDSecureInfo().isLiabilityShifted());
        assertEquals(authResponse.getCardNonce().getThreeDSecureInfo().isLiabilityShiftPossible(),
                parceled.getCardNonce().getThreeDSecureInfo().isLiabilityShiftPossible());
    }
}
