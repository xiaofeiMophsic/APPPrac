package com.example.imageviewpager;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.LruCache;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;

import com.example.imageviewpager.cache.BitmapCache;
import com.example.imageviewpager.fragment.ImageDetailFragment;
import com.example.imageviewpager.task.BitmapWorkerTask;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_IMAGE = "extra_image";
    public static final int[] imageIds = new int[]{
            R.drawable.a1, R.drawable.a2, R.drawable.a3,
            R.drawable.a4, R.drawable.a5, R.drawable.a6,
            R.drawable.a7, R.drawable.a8, R.drawable.a9,
            R.drawable.a10, R.drawable.a11, R.drawable.a12,
            R.drawable.a13
    };

    private ViewPager mViewPager;
    private ImagePagerAdapter mAdapter;
    private BitmapCache mBitmapCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_image_pager);

        mViewPager = (ViewPager) findViewById(R.id.id_pager);
        mAdapter = new ImagePagerAdapter(getSupportFragmentManager(), imageIds.length);
        mViewPager.setAdapter(mAdapter);

        mBitmapCache = new BitmapCache();
    }

    public void loadBitmap(int resId, ImageView imageView){
        final String imageKey = String.valueOf(resId);

        final Bitmap bitmap = mBitmapCache.getBitmapFromMemoryCache(imageKey);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }else {
            imageView.setImageResource(R.drawable.ic_photo_album_black_24dp);
            BitmapWorkerTask task = new BitmapWorkerTask(imageView, mBitmapCache, this);
            task.execute(resId);
        }
    }



    public static class ImagePagerAdapter extends FragmentStatePagerAdapter {

        private final int mSize;

        ImagePagerAdapter(FragmentManager fm, int size) {
            super(fm);
            mSize = size;
        }

        @Override
        public Fragment getItem(int position) {
            return ImageDetailFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return mSize;
        }
    }
}
