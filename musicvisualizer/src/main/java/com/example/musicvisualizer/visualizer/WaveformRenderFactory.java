package com.example.musicvisualizer.visualizer;

import android.support.annotation.ColorInt;

/**
 * Created by paozi on 2016/4/17.
 */
public class WaveformRenderFactory {

    public static WaveformRender createSimpleWaveformRender(@ColorInt int backgroundColor, @ColorInt int foregroundColor){
        return SimpleWaveform.newInstance(backgroundColor, foregroundColor);
    }
}
