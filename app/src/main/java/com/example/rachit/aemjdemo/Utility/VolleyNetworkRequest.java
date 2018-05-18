package com.example.rachit.aemjdemo.Utility;


import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class VolleyNetworkRequest extends StringRequest {

    private static final String TAG = "--MultipartRequest--";
    private static final int DEFAULT_TIMEOUT_MS = 20000;
    private static final int DEFAULT_MAX_RETRIES = 0;

    private final Response.Listener<String> mListener;
    private final Map<String, String> headerParams;
    private final String bodyParams;

    //Constructor
    public VolleyNetworkRequest(String url,
                                Response.ErrorListener errorListener,
                                Response.Listener<String> listener,
                                Map<String, String> headerParams,
                                String bodyParams) {
        super(Method.POST, url, listener, errorListener);
        Log.d(TAG, "VolleyNetworkRequest() called with: url = [" + url + "], listener = [" + listener + "], errorListener = [" + errorListener + "], headerParams = [" + headerParams + "], bodyParams = [" + bodyParams + "]");
        setRetryPolicy(new DefaultRetryPolicy(DEFAULT_TIMEOUT_MS,
                DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mListener = listener;
        this.bodyParams = bodyParams;
        this.headerParams = headerParams;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (headerParams == null)
            return super.getHeaders();
        else
            return headerParams;
    }

    @Override
    public String getBodyContentType() {
        return "application/json";
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
            if (bodyParams != null) {
//            final String request = new JSONObject(mBodyParams).toString();
                try {
                    return bodyParams.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", bodyParams, "utf-8");
                    return null;
                }
            } else {
                return null;
            }
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
            HashMap<String, String> map = new HashMap<>();
            JSONObject jObject = null;
            try {
                jObject = new JSONObject(bodyParams);
                Iterator<?> keys = jObject.keys();

                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    String value = jObject.getString(key);
                    map.put(key, value);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return map;
    }


    @SuppressWarnings("unchecked")
    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Log.e(TAG, "parseNetworkResponse: =" + json);
            return Response.success(json, HttpHeaderParser.parseCacheHeaders(response)); // it will return String
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }


}