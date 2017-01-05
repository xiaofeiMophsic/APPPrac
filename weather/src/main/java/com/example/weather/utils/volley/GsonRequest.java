package com.example.weather.utils.volley;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by paozi on 2016/4/5.
 */
public class GsonRequest<T> extends Request<T> {

    private final Response.Listener<T> mListener;
    private static Gson mGson = new Gson();

    private Class<T> mClass;
    private TypeToken<T> mTypeToken;

    public GsonRequest(int method, String url, Response.ErrorListener errorListener, Response.Listener<T> listener, Class<T> mClass) {
        super(method, url, errorListener);
        this.mListener = listener;
        this.mClass = mClass;
    }

    public GsonRequest(int method, String url, Response.ErrorListener errorListener, Response.Listener<T> listener, TypeToken token) {
        super(method, url, errorListener);
        this.mListener = listener;
        this.mTypeToken = token;
    }

    public GsonRequest(String url, Class clazz, Response.ErrorListener errorListener, Response.Listener<T> listener) {
        this(Method.GET, url, errorListener, listener, clazz);
    }

    public GsonRequest(String url, TypeToken<T> typeToken, Response.Listener<T> listener,Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        mTypeToken = typeToken;
        mListener = listener;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString  = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            if(mTypeToken == null){
                return Response.success(mGson.fromJson(jsonString, mClass), HttpHeaderParser.parseCacheHeaders(response));
            } else
                return (Response<T>) Response.success(mGson.fromJson(jsonString, mTypeToken.getType()), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }
}
