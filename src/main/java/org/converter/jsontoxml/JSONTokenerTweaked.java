package org.converter.jsontoxml;

import java.io.StringReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JSONTokenerTweaked extends JSONTokener {
	
	 private boolean eof;
	 
	 public JSONTokenerTweaked(String s) {
		 super(new StringReader(s));
		 //super(new StringReader(s));
	    }
	 
	public Object nextValueTweaked() throws JSONException {
        char c = this.nextClean();
        String string;

        switch (c) {
        case '"':
        case '\'':
            return this.nextString(c);
        case '{':
            this.back();
            return new JSONObjectLinkedHashMap(this);
        case '[':
            this.back();
            return new JSONArray(this);
        }

        /*
         * Handle unquoted text. This could be the values true, false, or
         * null, or it can be a number. An implementation (such as this one)
         * is allowed to also accept non-standard forms.
         *
         * Accumulate characters until we reach the end of the text or a
         * formatting character.
         */

        StringBuilder sb = new StringBuilder();
        while (c >= ' ' && ",:]}/\\\"[{;=#".indexOf(c) < 0) {
            sb.append(c);
            c = this.next();
        }
        if (!this.eof) {
            this.back();
        }

        string = sb.toString().trim();
        if ("".equals(string)) {
            throw this.syntaxError("Missing value");
        }
        return JSONObject.stringToValue(string);
    }

	
}
