package com.kareemwaleed.arxicttask.api_request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Kareem Waleed on 7/3/2017.
 */

public abstract class JsonParser {
    /**
     * Directs the parsing proccess based on the type of the object passed as a parameter
     */
    public static Map<String, Object> parser(Object json) throws JSONException {
        Map<String, Object> retMap = new HashMap<>();
        if (json != null) {
            if (json instanceof JSONObject)
                retMap = toMap((JSONObject) json);
            else if (json instanceof JSONArray)
                retMap = toList((JSONArray) json);
        }
        return retMap;
    }

    /**
     * Handles passed objects of JSONOject type
     */
    public static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<>();

        Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    /**
     * Handles passed object of JSONArray type
     */
    public static Map<String, Object> toList(JSONArray array) throws JSONException {
        Map<String, Object> list = new HashMap<>();
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.put(String.valueOf(i), value);
        }
        return list;
    }
}
