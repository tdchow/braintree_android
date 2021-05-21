package com.braintreepayments.api;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class AnalyticsConfigurationUnitTest {

    @Test
    public void fromJSON_parsesFullInput() throws JSONException {
        JSONObject input = new JSONObject()
                .put("url", "https://example.com/analytics");

        AnalyticsConfiguration sut = AnalyticsConfiguration.fromJSON(input);
        assertTrue(sut.isEnabled());
        assertEquals("https://example.com/analytics", sut.getUrl());
    }

    @Test
    public void fromJSON_whenInputNull_returnsConfigWithDefaultValues() {
        AnalyticsConfiguration sut = AnalyticsConfiguration.fromJSON(null);
        assertFalse(sut.isEnabled());
        assertNull(sut.getUrl());
    }

    @Test
    public void fromJSON_whenInputEmpty_returnsConfigWithDefaultValues() {
        AnalyticsConfiguration sut = AnalyticsConfiguration.fromJSON(new JSONObject());
        assertFalse(sut.isEnabled());
        assertNull(sut.getUrl());
    }
}
