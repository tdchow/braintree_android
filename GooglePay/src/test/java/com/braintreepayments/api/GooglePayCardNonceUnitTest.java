package com.braintreepayments.api;

import android.os.Parcel;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static com.braintreepayments.api.Assertions.assertBinDataEqual;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class GooglePayCardNonceUnitTest {

    @Test
    public void fromJson_createsGooglePayCardNonce() throws Exception {
        String response = Fixtures.PAYMENT_METHODS_GOOGLE_PAY_CARD_RESPONSE;
        JSONObject billing = new JSONObject(response).getJSONObject("paymentMethodData")
            .getJSONObject("info")
            .getJSONObject("billingAddress");
        JSONObject shipping = new JSONObject(response).getJSONObject("shippingAddress");

        PostalAddress billingPostalAddress = getPostalAddressObject(billing);
        PostalAddress shippingPostalAddress = getPostalAddressObject(shipping);

        GooglePayCardNonce googlePayCardNonce = GooglePayCardNonce.fromJson(response);

        assertEquals("Google Pay", googlePayCardNonce.getTypeLabel());
        assertEquals("fake-google-pay-nonce", googlePayCardNonce.getNonce());
        assertEquals("MasterCard 0276", googlePayCardNonce.getDescription());
        assertEquals("Visa", googlePayCardNonce.getCardType());
        assertEquals("11", googlePayCardNonce.getLastTwo());
        assertEquals("1234", googlePayCardNonce.getLastFour());
        assertEquals("android-user@example.com", googlePayCardNonce.getEmail());
        assertPostalAddress(billingPostalAddress, googlePayCardNonce.getBillingAddress());
        assertPostalAddress(shippingPostalAddress, googlePayCardNonce.getShippingAddress());
        assertTrue(googlePayCardNonce.isNetworkTokenized());
    }

    @Test
    public void fromJson_withoutBillingAddress_createsGooglePayCardNonce() throws Exception {
        String response = Fixtures.PAYMENT_METHODS_GOOGLE_PAY_CARD_RESPONSE;
        JSONObject json = new JSONObject(response);
        json.getJSONObject("paymentMethodData").getJSONObject("info").remove("billingAddress");
        response = json.toString();
        JSONObject billing = new JSONObject();

        PostalAddress billingPostalAddress = getPostalAddressObject(billing);

        GooglePayCardNonce googlePayCardNonce = GooglePayCardNonce.fromJson(response);

        assertPostalAddress(billingPostalAddress, googlePayCardNonce.getBillingAddress());
    }

    @Test
    public void fromJson_withoutShippingAddress_createsGooglePayCardNonce() throws Exception {
        String response = Fixtures.PAYMENT_METHODS_GOOGLE_PAY_CARD_RESPONSE;
        JSONObject json = new JSONObject(response);
        json.remove("shippingAddress");
        response = json.toString();
        JSONObject shipping = new JSONObject();

        PostalAddress shippingPostalAddress = getPostalAddressObject(shipping);

        GooglePayCardNonce googlePayCardNonce = GooglePayCardNonce.fromJson(response);

        assertPostalAddress(shippingPostalAddress, googlePayCardNonce.getShippingAddress());
    }

    @Test
    public void fromJson_withoutEmail_createsGooglePayCardNonce() throws JSONException {
        String response = Fixtures.PAYMENT_METHODS_GOOGLE_PAY_CARD_RESPONSE;

        JSONObject json = new JSONObject(response);
        json.remove("email");
        response = json.toString();

        GooglePayCardNonce googlePayCardNonce = GooglePayCardNonce.fromJson(response);

        assertEquals("", googlePayCardNonce.getEmail());
    }

    @Test
    public void parcelsCorrectly() throws Exception {
        String response = Fixtures.PAYMENT_METHODS_GOOGLE_PAY_CARD_RESPONSE;
        JSONObject billing = new JSONObject(response).getJSONObject("paymentMethodData")
                .getJSONObject("info")
                .getJSONObject("billingAddress");
        JSONObject shipping = new JSONObject(response).getJSONObject("shippingAddress");

        PostalAddress billingPostalAddress = getPostalAddressObject(billing);
        PostalAddress shippingPostalAddress = getPostalAddressObject(shipping);

        GooglePayCardNonce googlePayCardNonce = GooglePayCardNonce.fromJson(response);

        Parcel parcel = Parcel.obtain();
        googlePayCardNonce.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);

        GooglePayCardNonce parceled = GooglePayCardNonce.CREATOR.createFromParcel(parcel);

        assertEquals("Google Pay", parceled.getTypeLabel());
        assertEquals("fake-google-pay-nonce", parceled.getNonce());
        assertEquals("MasterCard 0276", parceled.getDescription());
        assertEquals("Visa", parceled.getCardType());
        assertEquals("11", parceled.getLastTwo());
        assertEquals("1234", parceled.getLastFour());
        assertEquals("android-user@example.com", parceled.getEmail());
        assertPostalAddress(billingPostalAddress, parceled.getBillingAddress());
        assertPostalAddress(shippingPostalAddress, parceled.getShippingAddress());

        assertBinDataEqual(googlePayCardNonce.getBinData(), parceled.getBinData());
    }

    private PostalAddress getPostalAddressObject(JSONObject address) throws JSONException {
        return new PostalAddress()
                .recipientName(Json.optString(address, "name", ""))
                .streetAddress(Json.optString(address, "address1", ""))
                .extendedAddress(
                        String.join("\n",
                                Json.optString(address, "address2", ""),
                                Json.optString(address, "address3", "")
                        ).trim())
                .locality(Json.optString(address, "locality", ""))
                .region(Json.optString(address, "administrativeArea", ""))
                .countryCodeAlpha2(Json.optString(address, "countryCode", ""))
                .postalCode(Json.optString(address, "postalCode", ""));
    }

    private void assertPostalAddress(PostalAddress expected, PostalAddress actual) {
        assertEquals(expected.toString(), actual.toString());
    }
}