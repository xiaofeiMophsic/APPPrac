package com.example.videoplayer;

import android.graphics.Rect;
import android.support.annotation.DrawableRes;
import android.util.Log;
import android.view.View;

import com.volokh.danylo.video_player_manager.manager.VideoItem;
import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.video_player_manager.meta.CurrentItemMetaData;
import com.volokh.danylo.video_player_manager.meta.MetaData;
import com.volokh.danylo.video_player_manager.ui.VideoPlayerView;
import com.volokh.danylo.visibility_utils.items.ListItem;

/**
 * Created by paozi on 2016/4/14.
 */
public abstract class VideoListItem implements VideoItem, ListItem{

    private String mTitle;
    private Rect mCurrentViewRect;
    @DrawableRes private final int mImageResource;
    private VideoPlayerManager<MetaData> mVideoPlayerManager;

    public VideoListItem(VideoPlayerManager<MetaData> playerManager, String title, @DrawableRes int imageResource) {
        mVideoPlayerManager = playerManager;
        mTitle = title;
        mImageResource = imageResource;
        mCurrentViewRect = new Rect();
    }

    public String getTitle() {
        return mTitle;
    }

    public int getImageResource(){
        return mImageResource;
    }

    /**
     * getLocalVisibleRect 就是获取当前view可见部分的矩形，这个矩形以自己的左上角为坐标原点，右下角为可见部分的宽高
     * @param view
     * @return
     */
    @Override
    public int getVisibilityPercents(View view) {
        int percent = 100;

        view.getLocalVisibleRect(mCurrentViewRect);
        int height = view.getHeight();

        if (viewPartiallyHiddenInTop()){
            percent = (height - mCurrentViewRect.top) * 100 / height ;
        } else if(viewPartiallyHiddenInBottom(height)){
            percent = mCurrentViewRect.bottom * 100 / height;
        }

        setVisibilePercent(view, percent);
        return percent;
    }

    @Override
    public void setActive(View newActiveView, int newActiveViewPosition) {
        Log.i("Video List Item", "setActive: 播放新的视频");
        VideoListAdapter.VideoViewHolder holder = (VideoListAdapter.VideoViewHolder) newActiveView.getTag();
        playNewVideo(new CurrentItemMetaData(newActiveViewPosition, newActiveView), holder.getVideoPlayerView(), mVideoPlayerManager);
    }

    @Override
    public void deactivate(View currentView, int position) {
        stopPlayback(mVideoPlayerManager);
    }

    @Override
    public void stopPlayback(VideoPlayerManager videoPlayerManager) {
        videoPlayerManager.stopAnyPlayback();
    }

    private boolean viewPartiallyHiddenInTop(){
        return mCurrentViewRect.top > 0;
    }

    private boolean viewPartiallyHiddenInBottom(int height){
        return mCurrentViewRect.bottom > 0 && mCurrentViewRect.bottom < height;
    }

    private void setVisibilePercent(View view, int percent) {

        VideoListAdapter.VideoViewHolder holder = (VideoListAdapter.VideoViewHolder) view.getTag();
        String percentStr = "可视大小：" + percent;
        holder.getVideoPercent().setText(percentStr);
    }

}
