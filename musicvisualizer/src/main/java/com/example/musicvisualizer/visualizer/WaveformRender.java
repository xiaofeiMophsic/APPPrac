package com.example.musicvisualizer.visualizer;

import android.graphics.Canvas;

/**
 * Created by paozi on 2016/4/17.
 */
public interface WaveformRender {
    void render(Canvas canvas, byte[] waveform);
}
