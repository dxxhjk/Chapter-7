package com.bytedance.videoplayer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.VideoView;
import android.widget.MediaController;

public class BrowsableActivity extends AppCompatActivity {

    private VideoView videoView;
    private int videoPosition = 0;
    private boolean isPlaying = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoView = findViewById(R.id.videoView);
        Intent intent = getIntent();
        Uri uri = intent.getData();
        videoView.setVideoURI(uri);
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoView);
        videoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoPosition = videoView.getCurrentPosition();
        isPlaying = videoView.isPlaying();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoView.seekTo(videoPosition);
        if (isPlaying)videoView.start();
    }

    @Override
    protected void onSaveInstanceState(@Nullable Bundle state) {
        if (state != null) {
            state.putInt("position", videoPosition);
            state.putBoolean("isPlaying", isPlaying);
        }
        super.onSaveInstanceState(state);
    }

    @Override
    protected void onRestoreInstanceState(@Nullable Bundle state) {
        super.onRestoreInstanceState(state);
        if (state != null) {
            int position = state.getInt("position");
            boolean isplaying = state.getBoolean("isPlaying");
            videoPosition = position;
            videoView.seekTo(position);
            isPlaying = isplaying;
            if (isPlaying)
                videoView.start();
        }
    }
}
