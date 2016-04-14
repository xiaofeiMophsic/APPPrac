package com.example.videoplayer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.volokh.danylo.video_player_manager.ui.MediaPlayerWrapper;
import com.volokh.danylo.video_player_manager.ui.VideoPlayerView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by paozi on 2016/4/14.
 */
public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoViewHolder>{

    private List<VideoListItem> mList;

    public VideoListAdapter(List<VideoListItem> list) {
        mList = list;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_video_item, parent);
        VideoViewHolder videoViewHolder = new VideoViewHolder(view);
        view.setTag(videoViewHolder);

        return videoViewHolder;
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        VideoListItem item = mList.get(position);
        holder.bindItemView(item);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.item_video_player) VideoPlayerView mVideoPlayerView;
        @Bind(R.id.item_video_cover) ImageView mImageCover;
        @Bind(R.id.item_video_title) TextView mVideoTitle;
        @Bind(R.id.item_video_percent) TextView mVideoPercent;

        private Context mContext;
        private MediaPlayerWrapper.MainThreadMediaPlayerListener mMediaPlayerListener;

        public VideoViewHolder(View view) {
            super(view);
            ButterKnife.bind(view);

            this.mContext = view.getContext().getApplicationContext();
            mMediaPlayerListener = new MediaPlayerWrapper.MainThreadMediaPlayerListener() {
                @Override
                public void onVideoSizeChangedMainThread(int width, int height) {

                }

                @Override
                public void onVideoPreparedMainThread() {
                    mImageCover.setVisibility(View.VISIBLE);
                }

                @Override
                public void onVideoCompletionMainThread() {

                }

                @Override
                public void onErrorMainThread(int what, int extra) {

                }

                @Override
                public void onBufferingUpdateMainThread(int percent) {

                }

                @Override
                public void onVideoStoppedMainThread() {
                    mImageCover.setVisibility(View.VISIBLE);
                }
            };

            mVideoPlayerView.addMediaPlayerListener(mMediaPlayerListener);
        }

        public VideoPlayerView getVideoPlayerView() {
            return mVideoPlayerView;
        }

        public TextView getVideoPercent() {
            return mVideoPercent;
        }

        public void bindItemView(VideoListItem item) {
            mVideoTitle.setText(item.getTitle());
            mImageCover.setVisibility(View.VISIBLE);
            Picasso.with(mContext).load(item.getImageResource()).into(mImageCover);
        }
    }

}
