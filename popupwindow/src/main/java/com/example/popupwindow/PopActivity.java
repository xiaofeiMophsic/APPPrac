package com.example.popupwindow;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

public class PopActivity extends Activity {
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);
        layout = (LinearLayout) findViewById(R.id.exit_layout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！",
                        Toast.LENGTH_SHORT).show();

            }

        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;

    }

    public void exitbutton1(View v) {
        this.finish();

    }

    public void exitbutton0(View v) {
        this.finish();
    }
}
