package com.braintreepayments.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * JSONPath inspired implementation for extracting values from a json string.
 *
 * @see <a href="https://goessner.net/articles/JsonPath">JSONPath - XPath for JSON</a>
 */
class BTJSON {

    JSONObject json;

    BTJSON(String json) throws JSONException {
        this.json = new JSONObject(json);
    }

    String getString(String path) throws JSONException {
        String result = getString(path, null);
        if (result == null) {
            throw new JSONException("property not found");
        }
        return result;
    }

    String getString(String path, String fallback) {
        List<String> pathComponents = Arrays.asList(path.split("\\."));
        Iterator<String> iterator = pathComponents.iterator();

        // discard root '$'
        iterator.next();

        // seek to end of path
        JSONObject currentNode = json;
        while (iterator.hasNext()) {
            String nextKey = iterator.next();
            try {
                if (iterator.hasNext()) {
                    currentNode = currentNode.getJSONObject(nextKey);
                } else {
                    // end of path reached; get string
                    return currentNode.getString(nextKey);
                }
            } catch (JSONException exception) {
                break;
            }
        }
        return fallback;
    }


    boolean getBoolean(String path, boolean fallback) {
        List<String> pathComponents = Arrays.asList(path.split("\\."));
        Iterator<String> iterator = pathComponents.iterator();

        // discard root '$'
        iterator.next();

        // seek to end of path
        JSONObject currentNode = json;
        while (iterator.hasNext()) {
            String nextKey = iterator.next();
            try {
                if (iterator.hasNext()) {
                    currentNode = currentNode.getJSONObject(nextKey);
                } else {
                    // end of path reached; get string
                    return currentNode.getBoolean(nextKey);
                }
            } catch (JSONException exception) {
                break;
            }
        }
        return fallback;
    }

    List<String> getStrings(String path, List<String> fallback) {
        List<String> pathComponents = Arrays.asList(path.split("\\."));
        Iterator<String> iterator = pathComponents.iterator();

        // discard root '$'
        iterator.next();

        // seek to end of path
        JSONObject currentNode = json;
        while (iterator.hasNext()) {
            String nextKey = iterator.next();
            try {
                if (iterator.hasNext()) {
                    currentNode = currentNode.getJSONObject(nextKey);
                } else {
                    String targetProperty = nextKey.replace("[*]", "");
                    // end of path reached; get string
                    JSONArray targetArray = currentNode.getJSONArray(targetProperty);

                    List<String> result = new ArrayList<>();
                    for (int i = 0; i < targetArray.length(); i++) {
                        result.add(targetArray.getString(i));
                    }
                    return result;
                }
            } catch (JSONException exception) {
                break;
            }
        }
        return fallback;
    }
}
