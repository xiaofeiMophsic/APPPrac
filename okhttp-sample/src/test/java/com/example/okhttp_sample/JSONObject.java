package com.example.okhttp_sample;

import org.json.JSONException;

/**
 * 作者：xiaofei
 * 日期：2016/8/9
 * 邮箱：paozi.xiaofei.123@gmail.com
 */
public class JSONObject {
    private org.json.JSONObject mJSONObject;

    public JSONObject(String jsonStr) throws JSONException {
        mJSONObject = new org.json.JSONObject(jsonStr);
    }

    public JSONObject(org.json.JSONObject jo) throws JSONException {
        this.mJSONObject = jo;
    }

    public JSONObject put(String name, String value) throws JSONException {
        synchronized (this) {
            mJSONObject.put(name, value);
            return this;
        }
    }

    public String getString(String name) throws JSONException {
        synchronized (this) {
            return mJSONObject.getString(name);
        }
    }

    public JSONObject getJSONObject(String name) throws JSONException {
        synchronized (this) {
            return new JSONObject(mJSONObject.getJSONObject(name));
        }
    }

}
