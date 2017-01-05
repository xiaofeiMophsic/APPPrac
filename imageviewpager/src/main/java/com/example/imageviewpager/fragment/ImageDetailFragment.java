package com.example.imageviewpager.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.imageviewpager.MainActivity;
import com.example.imageviewpager.R;

/**
 * 作者：xiaofei
 * 日期：2016/8/28
 * 邮箱：paozi.xiaofei.123@gmail.com
 */

public class ImageDetailFragment extends Fragment {

    private static final String IMAGE_DATA_EXTRA = "resId";

    private int mImageNumber;
    private ImageView mImageView;

    public static Fragment newInstance(int imageNum){
        final ImageDetailFragment f = new ImageDetailFragment();
        final Bundle bundle = new Bundle();
        bundle.putInt(IMAGE_DATA_EXTRA, imageNum);
        f.setArguments(bundle);
        return f;
    }

    public ImageDetailFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageNumber = getArguments() != null ? getArguments().getInt(IMAGE_DATA_EXTRA) : -1;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_image_detail, container, false);
        mImageView = (ImageView) view.findViewById(R.id.image);
        return  view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (MainActivity.class.isInstance(getActivity())) {
            final int resId = MainActivity.imageIds[mImageNumber];
            ((MainActivity)getActivity()).loadBitmap(resId, mImageView);
        }
    }
}
