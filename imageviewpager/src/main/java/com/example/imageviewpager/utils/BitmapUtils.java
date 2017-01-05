package com.example.imageviewpager.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * 作者：xiaofei
 * 日期：2016/8/28
 * 邮箱：paozi.xiaofei.123@gmail.com
 */

public class BitmapUtils {
    private BitmapUtils() {
        throw new UnsupportedOperationException("Can not be instantiated");
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        Log.e("BitmapUtils", options.inSampleSize + "");
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        int inSampleSize = 1;

        if (outWidth > reqWidth || outHeight > reqHeight) {
            final int halfWidth = outWidth / 2;
            final int halfHeight = outHeight / 2;

            while ((halfWidth / inSampleSize) >= reqWidth && (halfHeight / inSampleSize) >= reqHeight) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
