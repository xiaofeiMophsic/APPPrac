package com.example.videoplayer;


import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.volokh.danylo.video_player_manager.manager.PlayerItemChangeListener;
import com.volokh.danylo.video_player_manager.manager.SingleVideoPlayerManager;
import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.video_player_manager.meta.MetaData;
import com.volokh.danylo.visibility_utils.calculator.DefaultSingleItemCalculatorCallback;
import com.volokh.danylo.visibility_utils.calculator.ListItemsVisibilityCalculator;
import com.volokh.danylo.visibility_utils.calculator.SingleListViewItemActiveCalculator;
import com.volokh.danylo.visibility_utils.scroll_utils.ItemsPositionGetter;
import com.volokh.danylo.visibility_utils.scroll_utils.RecyclerViewItemPositionGetter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by paozi on 2016/4/13.
 */
public class VideoListFragment extends Fragment {
    public static final String VIDEO_TYPE_ARG = "com.example.videoplayer.video.type";
    private static final String URL = "http://dn-chunyu.qbox.me/fwb/static/images/home/video/video_aboutCY_A.mp4";
    private static final String[] LOCAL_VIDEO_NAMES = new String[]{
            "local_video_1.mp4",
            "local_video_2.mp4",
            "local_video_3.mp4",
            "local_video_4.mp4"
    };
    private static final String ONLINE_VIDEO_NAME = "douyu";
    @Bind(R.id.video_list)
    RecyclerView mRecyclerView;
    private List<VideoListItem> mList;
    private LinearLayoutManager mLayoutManager;
    private ItemsPositionGetter mItemsPositionGetter;
    private int mScrollState;
    private ListItemsVisibilityCalculator mVisibilityCalculator;
    private VideoPlayerManager<MetaData> mVideoPlayerManager;

    public VideoListFragment() {
        mList = new ArrayList<>();
        mVisibilityCalculator = new SingleListViewItemActiveCalculator(new DefaultSingleItemCalculatorCallback(), mList);
        mVideoPlayerManager = new SingleVideoPlayerManager(new PlayerItemChangeListener() {
            @Override
            public void onPlayerItemChanged(MetaData currentItemMetaData) {

            }
        });

        mScrollState = AbsListView.OnScrollListener.SCROLL_STATE_IDLE;
    }

    public static Fragment newInstance(int local) {
        VideoListFragment simpleFragment = new VideoListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(VIDEO_TYPE_ARG, local);
        simpleFragment.setArguments(bundle);
        return simpleFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        if (null != bundle) {
            if (bundle.getInt(VIDEO_TYPE_ARG) == MainActivity.LOCAL) {
                initLocalVideo();
            } else {
                initOnlineVideo();
            }
        } else {
            initLocalVideo();
        }

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        VideoListAdapter adapter = new VideoListAdapter(mList);
        mRecyclerView.setAdapter(adapter);

        mItemsPositionGetter = new RecyclerViewItemPositionGetter(mLayoutManager, mRecyclerView);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                mScrollState = newState;
                if (newState == RecyclerView.SCROLL_STATE_IDLE && !mList.isEmpty()) {
                    mVisibilityCalculator.onScrollStateIdle(mItemsPositionGetter,
                            mLayoutManager.findFirstVisibleItemPosition(),
                            mLayoutManager.findLastVisibleItemPosition());
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (!mList.isEmpty()) {
                    mVisibilityCalculator.onScroll(mItemsPositionGetter,
                            mLayoutManager.findFirstVisibleItemPosition(),
                            mLayoutManager.findLastVisibleItemPosition() - mLayoutManager.findFirstVisibleItemPosition() + 1,
                            mScrollState);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!mList.isEmpty()) {
            mRecyclerView.post(new Runnable() {
                @Override
                public void run() {
                    mVisibilityCalculator.onScrollStateIdle(mItemsPositionGetter,
                            mLayoutManager.findFirstVisibleItemPosition(),
                            mLayoutManager.findLastVisibleItemPosition());
                }
            });
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mVideoPlayerManager.resetMediaPlayer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void initLocalVideo() {
        for (int i = 0; i < 10; i++) {
            mList.add(new LocalVideoListItem(mVideoPlayerManager, LOCAL_VIDEO_NAMES[i % 4], R.drawable.avatar, getFileDes(LOCAL_VIDEO_NAMES[i % 4])));
        }
    }

    private void initOnlineVideo() {
        for (int i = 0; i < 10; i++){
            mList.add(new OnlineVideoListItem(mVideoPlayerManager, LOCAL_VIDEO_NAMES[i % 4], R.drawable.avatar, URL));
        }
    }

    private AssetFileDescriptor getFileDes(String fileName){
        try {
            return getActivity().getAssets().openFd(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
