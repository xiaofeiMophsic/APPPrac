package com.example.videoplayer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public static final int LOCAL = 0;
    public static final int ONLINE = 1;

    @Bind(R.id.main_toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mToolbar.setTitle("视频列表");
        setSupportActionBar(mToolbar);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_fl_container, VideoListFragment.newInstance(LOCAL))
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.enable_local_video:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fl_container, VideoListFragment.newInstance(LOCAL))
                        .commit();
            case R.id.enable_online_video:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fl_container, VideoListFragment.newInstance(ONLINE))
                        .commit();
        }

        item.setChecked(!item.isChecked());
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
