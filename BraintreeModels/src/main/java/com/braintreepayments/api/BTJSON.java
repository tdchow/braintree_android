package com.braintreepayments.api;

import java.util.List;

/**
 * JSONPath inspired implementation for extracting values from a json string.
 *
 * @see <a href="https://goessner.net/articles/JsonPath">JSONPath - XPath for JSON</a>
 */
class BTJSON {

    BTJSON(String json) {

    }

    boolean getBoolean(String path, boolean fallback) {
        return false;
    }

    String getString(String path) {
        return null;
    }

    String getString(String path, String fallback) {
        return null;
    }

    List<String> getStrings(String path, List<String> fallback) {
        return null;
    }
}
