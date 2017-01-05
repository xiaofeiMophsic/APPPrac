package com.example.imageviewpager.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.imageviewpager.cache.BitmapCache;
import com.example.imageviewpager.utils.BitmapUtils;
import com.example.imageviewpager.utils.DensityUtils;

import java.lang.ref.WeakReference;

/**
 * 作者：xiaofei
 * 日期：2016/8/28
 * 邮箱：paozi.xiaofei.123@gmail.com
 */

public class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {

    private final WeakReference<ImageView> mImageViewWeakReference;
    private final WeakReference<Context> mContextWeakReference;
    private final WeakReference<BitmapCache> mBitmapCacheReference;
    private int resId;

    public BitmapWorkerTask(ImageView imageView, BitmapCache bitmapCache, Context context) {
        mImageViewWeakReference = new WeakReference<ImageView>(imageView);
        mContextWeakReference = new WeakReference<Context>(context);
        mBitmapCacheReference = new WeakReference<BitmapCache>(bitmapCache);
    }

    @Override
    protected Bitmap doInBackground(Integer... integers) {
        resId = integers[0];
        Context context = mContextWeakReference.get();
        Bitmap bitmap = BitmapUtils.decodeSampledBitmapFromResource(context.getResources(), resId, 300, 300);
        //将bitmap添加到缓存中
        mBitmapCacheReference.get().addBitmapToCache(String.valueOf(resId), bitmap);
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null) {
            final ImageView imageView = mImageViewWeakReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
