package com.example.musicvisualizer.visualizer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.ColorInt;

/**
 * Created by paozi on 2016/4/17.
 */
public class SimpleWaveform implements WaveformRender{

    private static final int Y_FACTOR = 0xFF;
    private static final float HALF_FACTOR = 0.5f;

    @ColorInt private final int mBackgroundColor;
    private final Paint mForgePaint;
    private final Path mWavePath;

    private SimpleWaveform(int backgroundColor, Paint forgePaint, Path wavePath) {
        mBackgroundColor = backgroundColor;
        mForgePaint = forgePaint;
        mWavePath = wavePath;
    }

    public static SimpleWaveform newInstance(@ColorInt int backgroundColor, @ColorInt int foregroundColor){
        Paint paint = new Paint();
        paint.setColor(foregroundColor);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(8.0f);
        paint.setStyle(Paint.Style.STROKE);

        Path path = new Path();
        return new SimpleWaveform(backgroundColor, paint, path);
    }

    @Override
    public void render(Canvas canvas, byte[] waveform) {

        canvas.drawColor(mBackgroundColor);
        int height = canvas.getHeight();
        int width = canvas.getWidth();
        mWavePath.reset();

        if(waveform != null){
            renderWaveForm(waveform, width, height);
        }else {
            renderBlank(width, height);
        }

        canvas.drawPath(mWavePath, mForgePaint);
    }

    private void renderBlank(int width, int height) {
        int halfHeight = (int) (height * HALF_FACTOR);
        mWavePath.moveTo(0, halfHeight);
        mWavePath.lineTo(width, halfHeight);
    }

    private void renderWaveForm(byte[] waveform, int width, int height) {
        int xIncrement = width / waveform.length;
        int yIncrement = height / Y_FACTOR;

        int halfHeight = (int) (height * HALF_FACTOR);
        mWavePath.moveTo(0, 128 * yIncrement);
        int waveLength = waveform.length;
        for(int i = 0; i < waveLength; i++){
            int y = waveform[i] > 0 ? height - (waveform[i] * yIncrement) : -(waveform[i] * yIncrement);
            mWavePath.lineTo(xIncrement * i, y);
        }
        mWavePath.lineTo(width, 128 * yIncrement);
    }
}
