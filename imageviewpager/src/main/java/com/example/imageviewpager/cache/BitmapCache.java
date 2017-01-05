package com.example.imageviewpager.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.text.TextUtils;

/**
 * 作者：xiaofei
 * 日期：2016/8/28
 * 邮箱：paozi.xiaofei.123@gmail.com
 */

public class BitmapCache {

    private LruCache<String, Bitmap> mMemoryCache;

    public BitmapCache() {
        //LruCache
        final int maxMemory = (int)(Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount() / 1024;
            }
        };

    }

    public void addBitmapToCache(String key, Bitmap bitmap){
        if (getBitmapFromMemoryCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }

    }

    public Bitmap getBitmapFromMemoryCache(String key) {
        if (TextUtils.isEmpty(key)) {
            throw new IllegalArgumentException("key can not be null!");
        }
        return mMemoryCache.get(key);
    }

}
