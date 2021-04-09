package com.braintreepayments.api;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import static junit.framework.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class VisaCheckoutAccountUnitTest {

    @Test
    public void build_withNullVisaPaymentSummary_buildsEmptyPaymentMethod() throws JSONException {
        JSONObject expectedBase = new JSONObject()
                .put("visaCheckoutCard", new JSONObject())
                .put("_meta", new JSONObject()
                        .put("source", "form")
                        .put("integration", "custom")
                        .put("sessionId", "1234")
                        .put("platform", "android")
                );

        VisaCheckoutAccount visaCheckoutAccount = new VisaCheckoutAccount(null);
        visaCheckoutAccount.setSessionId("1234");
        JSONObject json = visaCheckoutAccount.buildJSON();

        JSONAssert.assertEquals(expectedBase, json, JSONCompareMode.STRICT);
    }

    @Test
    public void build_withVisaPaymentSummary_buildsExpectedPaymentMethod() throws JSONException {
        VisaCheckoutPaymentSummary visaCheckoutPaymentSummary = new VisaCheckoutPaymentSummary("stubbedCallId", "stubbedEncKey", "stubbedEncPaymentData");

        VisaCheckoutAccount visaCheckoutAccount = new VisaCheckoutAccount(visaCheckoutPaymentSummary);
        visaCheckoutAccount.setSessionId("1234");
        JSONObject json = visaCheckoutAccount.buildJSON();

        JSONObject expectedBase = new JSONObject();
        JSONObject expectedPaymentMethodNonce = new JSONObject();
        expectedPaymentMethodNonce.put("callId", "stubbedCallId");
        expectedPaymentMethodNonce.put("encryptedKey", "stubbedEncKey");
        expectedPaymentMethodNonce.put("encryptedPaymentData", "stubbedEncPaymentData");
        expectedBase.put("visaCheckoutCard", expectedPaymentMethodNonce);

        expectedBase.put("_meta", new JSONObject()
                .put("source", "form")
                .put("integration", "custom")
                .put("sessionId", "1234")
                .put("platform", "android")
        );

        JSONAssert.assertEquals(expectedBase, json, JSONCompareMode.STRICT);
    }

    @Test
    public void getApiPath_returnsCorrectApiPath() {
        assertEquals("visa_checkout_cards", new VisaCheckoutAccount(null).getApiPath());
    }
}
