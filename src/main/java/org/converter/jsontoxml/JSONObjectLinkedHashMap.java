package org.converter.jsontoxml;

import java.util.*;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.*;

public class JSONObjectLinkedHashMap extends JSONObject{
	
	private final Map<String, Object> map;
	
	 public JSONObjectLinkedHashMap() {
	        // HashMap is used on purpose to ensure that elements are unordered by 
	        // the specification.
	        // JSON tends to be a portable transfer format to allows the container 
	        // implementations to rearrange their items for a faster element 
	        // retrieval based on associative access.
	        // Therefore, an implementation mustn't rely on the order of the item.
	        this.map = new LinkedHashMap<String, Object>();
	    }
	
	 public JSONObjectLinkedHashMap put(String key, Object value) throws JSONException {
	        if (key == null) {
	            throw new NullPointerException("Null key.");
	        }
	        if (value != null) {
	            testValidity(value);
	            this.map.put(key, value);
	        } else {
	            this.remove(key);
	        }
	        return this;
	    }
	 
	 public JSONObjectLinkedHashMap(JSONTokenerTweaked x) throws JSONException {
	        this();
	        char c;
	        String key;

	        if (x.nextClean() != '{') {
	            throw x.syntaxError("A JSONObject text must begin with '{'");
	        }
	        for (;;) {
	            c = x.nextClean();
	            switch (c) {
	            case 0:
	                throw x.syntaxError("A JSONObject text must end with '}'");
	            case '}':
	                return;
	            default:
	                x.back();
	                key = x.nextValueTweaked().toString();
	            }

	            // The key is followed by ':'.

	            c = x.nextClean();
	            if (c != ':') {
	                throw x.syntaxError("Expected a ':' after a key");
	            }
	            
	            // Use syntaxError(..) to include error location
	            
	            if (key != null) {
	                // Check if key exists
	                if (this.opt(key) != null) {
	                    // key already exists
	                    throw x.syntaxError("Duplicate key \"" + key + "\"");
	                }
	                // Only add value if non-null
	                Object value = x.nextValueTweaked();
	                if (value!=null) {
	                    this.put(key, value);
	                }
	            }

	            // Pairs are separated by ','.

	            switch (x.nextClean()) {
	            case ';':
	            case ',':
	                if (x.nextClean() == '}') {
	                    return;
	                }
	                x.back();
	                break;
	            case '}':
	                return;
	            default:
	                throw x.syntaxError("Expected a ',' or '}'");
	            }
	        }
	    }
	
	
	 public JSONObjectLinkedHashMap(String source) throws JSONException {
	        this(new JSONTokenerTweaked(source));
	    }
	 
	 @Override
	 public Set<Entry<String, Object>> entrySet() {
	        return this.map.entrySet();
	    }
	 
	@Override
	public Map<String, Object> toMap() {
        Map<String, Object> results = new LinkedHashMap<String, Object>();
        for (Entry<String, Object> entry : this.entrySet()) {
            Object value;
            if (entry.getValue() == null || NULL.equals(entry.getValue())) {
                value = null;
            } else if (entry.getValue() instanceof JSONObject) {
                value = ((JSONObject) entry.getValue()).toMap();
            } else if (entry.getValue() instanceof JSONArray) {
                value = ((JSONArray) entry.getValue()).toList();
            } else {
                value = entry.getValue();
            }
            results.put(entry.getKey(), value);
        }
        return results;
    }
}
