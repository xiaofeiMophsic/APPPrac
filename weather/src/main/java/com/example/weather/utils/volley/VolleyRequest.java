package com.example.weather.utils.volley;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

/**
 * Created by paozi on 2016/4/5.
 */
public class VolleyRequest {

    private static RequestQueue requestQueue;

    public VolleyRequest(){

    }

    public static void buildRequestQueue(Context context){
        requestQueue = Volley.newRequestQueue(context);
    }

    public static VolleyRequest newInstance(){
        if(null == requestQueue){
            throw new NullPointerException("can't build request queue first");
        }
        return new VolleyRequest();
    }

    public <T> GsonRequest<T> newGsonRequest(String url, Class<T> clazz, Response.Listener<T> listener, Response.ErrorListener errorListener){
        GsonRequest request = new GsonRequest(url, clazz, errorListener, listener);
        requestQueue.add(request);
        return request;
    }
}
