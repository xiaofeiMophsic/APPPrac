package com.example.videoplayer;


import android.support.annotation.DrawableRes;

import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.video_player_manager.meta.MetaData;
import com.volokh.danylo.video_player_manager.ui.VideoPlayerView;

/**
 * Created by paozi on 2016/4/14.
 */
public class OnlineVideoListItem extends VideoListItem{
    private String url;
    public OnlineVideoListItem(VideoPlayerManager<MetaData> playerManager, String title, @DrawableRes int imageResource, String url) {
        super(playerManager, title, imageResource);
        this.url = url;
    }

    @Override
    public void playNewVideo(MetaData currentItemMetaData, VideoPlayerView player, VideoPlayerManager<MetaData> videoPlayerManager) {
        videoPlayerManager.playNewVideo(currentItemMetaData, player, url);
    }
}
