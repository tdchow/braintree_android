package com.braintreepayments.api;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class UnionPayConfigurationUnitTest {

    @Test
    public void fromJSON_parsesFullInput() throws JSONException {
        JSONObject input = new JSONObject()
                .put("enabled", true);

        UnionPayConfiguration sut = UnionPayConfiguration.fromJSON(input);
        assertTrue(sut.isEnabled());
    }

    @Test
    public void fromJSON_whenInputNull_returnsConfigWithDefaultValues() {
        UnionPayConfiguration sut = UnionPayConfiguration.fromJSON(null);
        assertFalse(sut.isEnabled());
    }

    @Test
    public void fromJSON_whenInputEmpty_returnsConfigWithDefaultValues() {
        UnionPayConfiguration sut = UnionPayConfiguration.fromJSON(new JSONObject());
        assertFalse(sut.isEnabled());
    }
}
