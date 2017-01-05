package com.example.musicvisualizer;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.media.audiofx.Visualizer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.musicvisualizer.permissions.PermissionActivity;
import com.example.musicvisualizer.permissions.PermissionsChecker;
import com.example.musicvisualizer.visualizer.WaveformRenderFactory;
import com.example.musicvisualizer.visualizer.WaveformView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.MODIFY_AUDIO_SETTINGS,
            Manifest.permission.RECORD_AUDIO
    };
    private static final int CAPTURE_SIZE = 256;

    @Bind(R.id.id_waveform) WaveformView mWaveformView;
    private Visualizer mVisualizer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        WaveformRenderFactory waveformRenderFactory = new WaveformRenderFactory();
        mWaveformView.setRender(waveformRenderFactory.createSimpleWaveformRender(Color.WHITE, ContextCompat.getColor(this, R.color.colorPrimary)));
    }

    @Override
    protected void onResume() {
        super.onResume();

        PermissionsChecker checker = new PermissionsChecker(this);
        if(checker.lakesPermissions(PERMISSIONS)){
            PermissionActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
        }else{
            startVisualizer();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE){
            if (resultCode == PermissionActivity.PERMISSION_GRANTED){
                Toast.makeText(this, "成功获取所有权限", Toast.LENGTH_SHORT).show();
            }else if(resultCode == PermissionActivity.PERMISSION_DENIED){
                Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void startVisualizer() {
        mVisualizer = new Visualizer(0);
        mVisualizer.setDataCaptureListener(new Visualizer.OnDataCaptureListener() {
            @Override
            public void onWaveFormDataCapture(Visualizer visualizer, byte[] waveform, int samplingRate) {
                if (mWaveformView != null) {
                    mWaveformView.setWaveform(waveform);
                }
            }

            @Override
            public void onFftDataCapture(Visualizer visualizer, byte[] fft, int samplingRate) {
                Log.i("当前捕获频率", "onFftDataCapture: " + fft);
            }
        }, mVisualizer.getMaxCaptureRate(), true, false);
        mVisualizer.setCaptureSize(CAPTURE_SIZE);
        mVisualizer.setEnabled(true);
    }

    @Override
    protected void onPause() {
        if(mVisualizer != null){
            mVisualizer.setEnabled(false);
            mVisualizer.release();
        }
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
