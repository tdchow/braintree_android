package com.braintreepayments.api;

import com.braintreepayments.api.GraphQLConstants.Features;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

@RunWith(RobolectricTestRunner.class)
public class GraphQLConfigurationUnitTest {

    @Test
    public void fromJSON_parsesFullInput() throws JSONException {
        JSONObject input = new JSONObject()
                .put("url", "https://example.com/graphql")
                .put("features", new JSONArray()
                        .put("tokenize_credit_cards")
                );
        GraphQLConfiguration sut = GraphQLConfiguration.fromJSON(input);
        assertTrue(sut.isEnabled());
        assertTrue(sut.isFeatureEnabled(Features.TOKENIZE_CREDIT_CARDS));
        assertEquals("https://example.com/graphql", sut.getUrl());
    }

    @Test
    public void fromJSON_whenInputNull_returnsConfigWithDefaultValues() {
       GraphQLConfiguration sut = GraphQLConfiguration.fromJSON(null);
       assertFalse(sut.isEnabled());
       assertFalse(sut.isFeatureEnabled(Features.TOKENIZE_CREDIT_CARDS));
       assertEquals("", sut.getUrl());
    }

    @Test
    public void fromJSON_whenInputEmpty_returnsConfigWithDefaultValues() {
        GraphQLConfiguration sut = GraphQLConfiguration.fromJSON(new JSONObject());
        assertFalse(sut.isEnabled());
        assertFalse(sut.isFeatureEnabled(Features.TOKENIZE_CREDIT_CARDS));
        assertEquals("", sut.getUrl());
    }
}
