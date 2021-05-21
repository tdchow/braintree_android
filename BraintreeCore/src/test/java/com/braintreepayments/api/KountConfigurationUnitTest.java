package com.braintreepayments.api;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

@RunWith(RobolectricTestRunner.class)
public class KountConfigurationUnitTest {

    @Test
    public void fromJSON_parsesFullInput() throws JSONException {
        JSONObject input = new JSONObject()
                .put("kountMerchantId", "123456");

        KountConfiguration sut = KountConfiguration.fromJSON(input);
        assertTrue(sut.isEnabled());
        assertEquals("123456", sut.getKountMerchantId());
    }

    @Test
    public void fromJSON_whenInputNull_returnsConfigWithDefaultValues() {
       KountConfiguration sut = KountConfiguration.fromJSON(null);
       assertFalse(sut.isEnabled());
       assertEquals("", sut.getKountMerchantId());
    }

    @Test
    public void fromJSON_whenInputEmpty_returnsConfigWithDefaultValues() {
        KountConfiguration sut = KountConfiguration.fromJSON(new JSONObject());
        assertFalse(sut.isEnabled());
        assertEquals("", sut.getKountMerchantId());
    }
}
