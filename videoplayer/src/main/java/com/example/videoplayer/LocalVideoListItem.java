package com.example.videoplayer;

import android.content.res.AssetFileDescriptor;
import android.support.annotation.DrawableRes;

import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.video_player_manager.meta.MetaData;
import com.volokh.danylo.video_player_manager.ui.VideoPlayerView;

/**
 * Created by paozi on 2016/4/14.
 */
public class LocalVideoListItem extends VideoListItem{

    private final AssetFileDescriptor mAssetFileDescriptor;

    public LocalVideoListItem(VideoPlayerManager<MetaData> playerManager, String title, @DrawableRes int imageResource, AssetFileDescriptor fileDescriptor) {
        super(playerManager, title, imageResource);
        mAssetFileDescriptor = fileDescriptor;
    }

    @Override
    public void playNewVideo(MetaData currentItemMetaData, VideoPlayerView player, VideoPlayerManager<MetaData> videoPlayerManager) {
        videoPlayerManager.playNewVideo(currentItemMetaData, player, mAssetFileDescriptor);
    }
}
